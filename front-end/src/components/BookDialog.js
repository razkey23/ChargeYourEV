import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import MenuItem from "@material-ui/core/MenuItem";
import CloseIcon from '@material-ui/icons/Close';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import Divider from '@material-ui/core/Divider';

import styled from 'styled-components';


import {
  ThemeProvider,
  createMuiTheme,
} from '@material-ui/core/styles';
import { amber } from '@material-ui/core/colors';

const theme = createMuiTheme({
  palette: {
    primary: amber,
  },
});


const useStyles = makeStyles(theme => ({
  root: {
    padding: `${theme.spacing.unit * 6}px ${theme.spacing.unit * 3}px 0`,
    display: 'flex',
    flexWrap: 'wrap',
  },
  margin: {
    margin: theme.spacing(1),
  },
  withoutLabel: {
    marginTop: theme.spacing(3),
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing.unit / 2,
    top: theme.spacing.unit / 2,
    color: theme.palette.grey[900],
  },
}));

export default function BookDialog(props) {

  const classes = useStyles();

  const [form, setForm] = useState({
    ownerUsername: props.userState.username,
    ownerId: props.userState.userid,
  });

  const [charger, setCharger] = useState(false)

  const [submitForm, setSubmitForm] = useState({})


  const [vehiclesList, setVehiclesList] = useState([])
  const [selectedVehicle, setSelectedVehicle] = React.useState();

  const [stationsList, setStationsList] = useState([])
  const [selectedStation, setSelectedStation] = useState(0)

  const [pointsList, setPointsList] = useState([])
  const [selectedPoint, setSelectedPoint] = React.useState(0)

  const [price, setPrice] = useState(0)
  const [cost, setCost] = useState(0)

  const [stop, setStop] = useState(false)
  const [startingTime, setStartingTime] = useState()

  const handleInputChange = e => {
    const { id, value } = e.target;
    setForm({ ...form, [id]: value });
    //console.log('this is my form', form);
  };

  const handleInputChangeSelectedVehicle = e => {
    const { id, value } = e.target;
    //console.log(value)
    setSelectedVehicle(value);
  };

  const handleInputChangeSelectedStation = e => {
    const { id, value } = e.target;
    setSelectedStation(value);
    //console.log('test selected station', selectedStation)
    const url = "https://localhost:8765/web/stations/chargingPoints/" + value;
    const points = []
    fetch(url)
        // We get the API response and receive data in JSON format...
      .then(response => response.json())
      .then(data => {
        data.forEach(d => {
          const p = {
            pointId: d.pointID,
            costPerKwh: d.costPerKwh,
            pricingPolicy: d.pricingPolicy,
          };
        //console.log(p);
        points.push(p);
        });
        //console.log('there are the points: ')
        setPointsList(points)
      });
    
      
  };

  const handleInputChangeSelectedPoint = e => {
    const { id, value } = e.target;
    handleReset()
    setSelectedPoint(value);
    const url = "https://localhost:8765/web/points/" + value;
    fetch(url)
      .then(response => response.json())
      .then(data => {
        setPrice(data.costPerKwh)
      });
  };

  
  //stin lista dropdown einai apothikeumena ola ta oximata tou logged in xristi
  useEffect(() => {
    const url = "https://localhost:8765/web/vehicles/" + form.ownerId;
    const dropdown = []
    fetch(url)
      .then(response => response.json())
      .then(data => {
        //console.log(data)
        data.forEach(m => {
          const temp = {
            id: m.vehicleID,
            type: m.vehicleType,
          };
          dropdown.push(temp);
        });
      })
      setVehiclesList(dropdown);
  }, []);

  
  useEffect(() => {
    const stations = []
    fetch(`https://localhost:8765/web/allstations`)
      // We get the API response and receive data in JSON format...
      .then(response => response.json())
      .then(data => {
        data.forEach(d => {
        console.log(stations)
          const p = {
            id: d.stationID,
            name: d.stationName,
            busyFactor: d.busyFactor,
          };
        //console.log(p);
         
        stations.push(p);
        });
      });
      //console.log(stations)
      setStationsList(stations);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    //ownerid = ??
    const f = { ...form };
    //f.ownerId = ownerId;
    console.log(f);
    setSubmitForm(f);
  }

  

  function handlePayment(){
    //props.handleClose()
    //console.log('just clicked ')
    setCharger(true)
    setStop(true)
    var timeNow = new Date();
    setStartingTime(timeNow)
   
  }

  function handleStop(){
    setStop(false)
    var timeNow = new Date();
    var duration = (timeNow.getTime() - startingTime.getTime()) / 1000;
    setCost(price*duration)
  }

  function handleReset(){
    setCost(0)
  }



  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Charging menu</DialogTitle>
        <IconButton aria-label="Close" className={classes.closeButton} onClick={props.handleClose}>
          <CloseIcon />
        </IconButton>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <DialogContentText>
              Please, select vehicle and charging point!
          </DialogContentText>
          <Divider />
          <br />
          <ThemeProvider theme={theme}>
            <TextField
              variant="outlined"
              id="listOfVehicles"
              select
              label="Select"
              className={classes.margin}
              style = {{width: 525}}
              value={selectedVehicle}
              onChange={handleInputChangeSelectedVehicle}
              helperText="Select your vehicle to be charged"
            >
              {vehiclesList.map(option => (
                <MenuItem key={option.id} value={option.id}>
                  {option.type}
                </MenuItem>
              ))}
            </TextField>

            <TextField
              variant="outlined"
              id="listOfStations"
              select
              label="Select"
              className={classes.margin}
              style = {{width: 525}}
              value={selectedStation}
              onChange={handleInputChangeSelectedStation}
              helperText="Select your station"
            >
              {stationsList.map(option => (
                  <MenuItem key={option.id} value={option.id}>
                    {option.name}
                  </MenuItem>
                  
              ))}
            </TextField>

            <TextField
              variant="outlined"
              id="listOfPoints"
              select
              label="Select"
              className={classes.margin}
              style = {{width: 525}}
              value={selectedPoint}
              onChange={handleInputChangeSelectedPoint}
              helperText="Select the charging point"
            >
              {pointsList.map(option => (
                <MenuItem key={option.pointId} value={option.pointId}>
                  {'Number of point is '}{option.pointId} {' with Cost/Kwh = '} {option.costPerKwh}
                </MenuItem>
                
              ))}
            </TextField>


          </ThemeProvider>
          </DialogContent>
          <DialogActions style={{ marginRight: 30 }}>
          <ThemeProvider theme={theme}>
              {selectedPoint !== 0 ? (
                <Button onClick={handlePayment} color="primary">
                  Initiate Charging
                </Button>
              ) : null}
            <Button onClick={props.handleClose} color="secondary">
                Cancel
            </Button>
            {stop ? (
                <Button onClick={handleStop} color="primary">
                  Stop
                </Button>
              ) : null}
            {charger ? (  
              <TextField
                  variant="outlined"
                  id="last"
                  label="Total price (€)"
                  className={classes.margin}
                  style = {{width: 525}}
                  value={cost}
              >
              </TextField>
            ): null}
            {(!stop && charger) ? (  
              <Button onClick={handleReset} color="secondary">
                Reset
              </Button>
            ): null}
          </ThemeProvider>
          </DialogActions>
        </form>
      </Dialog>
    </div>
  );
}