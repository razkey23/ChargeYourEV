import React, { useState, useEffect, PureComponent } from "react";
import ReactMapGL, { GeolocateControl, Marker, Popup, NavigationControl, FullscreenControl, ScaleControl } from "react-map-gl";

//import FormDialog from "./FormDialog";
import EvStationIcon from '@material-ui/icons/EvStation';
import { grey } from '@material-ui/core/colors';
import Divider from '@material-ui/core/Divider';
import { makeStyles } from '@material-ui/core/styles';
//import classes from "*.module.css";

const useStyles = makeStyles(theme => ({
  link: {
    textDecoration: 'none',
    color: '#e63f67',
  },
  title: {
    textAlign: 'center',
  },
  container: {
    display: 'flex',
    justifyContent: 'space-evenly',
  },
}));

// ---- GEOCODE information ---- //

var geo = require('mapbox-geocoding');
geo.setAccessToken(process.env.REACT_APP_MAPBOX_TOKEN);

// ---- STYLING w/ styled-components ---- //

// const Div = styled.div`
//   text-align: center;
// `;

// ---- Custom styling ---- //

const geolocateStyle = {
  position: 'fixed',
  bottom: 30,
  right: 0,
  margin: 10
};

const fullscreenControlStyle = {
  position: 'fixed',
  bottom: 158,
  right: 0,
  margin: 10
};

const navStyle = {
  position: 'fixed',
  bottom: 65,
  right: 0,
  margin: 10
};

const scaleControlStyle = {
  position: 'fixed',
  bottom: 10,
  right: 0,
  margin: 10
};



export default function Map(props) {

  const classes = useStyles();

  const [viewport, setViewport] = useState({
    latitude: 39.55493,
    longitude: 21.76837,
    width: "100vw",
    height: "95vh",
    zoom: 10
  });

  const [selectedPoint, setSelectedPoint] = useState(null);
  const [points, setPoints] = useState([]);
  const reg = /^-?([1-8]?[1-9]|[1-9]0)\.{1}\d{1,6}/

  const address = [];

  useEffect(() => {
    fetch(`https://localhost:8765/web/allstations`)
      // We get the API response and receive data in JSON format...
      .then(response => response.json())
      .then(data => {
        //console.log(data);
        data.forEach(d => {
          const p = {
            id: d.stationID,
            long: d.stationLongitude,
            lat: d.stationLatitude,
            name: d.stationName,
            busyFactor: d.busyFactor,
            providerName: d.energyProvider.providerName,
            providerID: d.energyProvider.providerID
          };
        //console.log(p);
          if (reg.test(p.lat)) {
            address.push(p);
          }
        });
      });
    props.setMasterPoint(address);
    setPoints(address);

    const listener = (e) => {
      if (e.key === "Escape") {
        setSelectedPoint(null);
      }
    };
    window.addEventListener("keydown", listener);

    return () => {
      window.removeEventListener("keydown", listener);
    }
  }, []);

  function PinIconType(props) {
    return <EvStationIcon
        style={{ color: grey[800] }}
        onClick={e => {
          e.preventDefault();
          setSelectedPoint(props.point);
        }}
        //eventHandlers={{click: e => {
            //e.preventDefault();
            //setSelectedPoint(props.point);} 
        //}}
      ></EvStationIcon>
  }


  //Improve Map performance -- Do no re-render map in every interaction
  class MarkersPoint extends PureComponent {
    render() {
      return this.props.masterPoint.map(
        point => <Marker key={point.id} latitude={Number(point.lat)} longitude={Number(point.long) }> <PinIconType point={point} /></Marker>
      )
    }
  }

  return (
    <div>
      <ReactMapGL
        {...viewport}
        mapboxApiAccessToken={process.env.REACT_APP_MAPBOX_TOKEN}
        mapStyle="mapbox://styles/lealinin/ck6u2qfkt1sp51imqh3o4jr3b?optimized=true"
        onViewportChange={(viewport) => {
          setViewport(viewport);
        }}
      >
        {/* MAPBOX CONTROLS BELOW */}
        <div style={fullscreenControlStyle}>
          <FullscreenControl />
        </div>
        <div style={navStyle}>
          <NavigationControl />
        </div>
        <div style={scaleControlStyle}>
          <ScaleControl />
        </div>
        <GeolocateControl
          style={geolocateStyle}
          positionOptions={{ enableHighAccuracy: true }}
          trackUserLocation={true}
        />

        {/* MAPBOX PINS BELOW */}
        <MarkersPoint masterPoint={props.masterPoint} />
        
        {selectedPoint ? (
          <Popup
            latitude={Number(selectedPoint.lat)}
            longitude={Number(selectedPoint.long)}
            onClose={() => {
              setSelectedPoint(null);
            }}
          >
            <h2 className={classes.name}>{selectedPoint.name}</h2>
            <Divider />
            <p>
              <b>Id:</b>{" "}
              {selectedPoint.id}
            </p>
            <p>
              <b>Busy Factor:</b>{" "}
              {selectedPoint.busyFactor}
            </p>
            <p>
              <b>Provider:</b>{" "}
              {selectedPoint.providerName}
            </p>
          </Popup>
        ) : null}
        
      </ReactMapGL>
    </div>
  )
}