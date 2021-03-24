import './App.css';
import React, { useState } from "react";
import Map from "../components/Map";
import Navbar from "../components/Navbar";
import DialogeLogin from "../components/DialogeLogin";
import BookDialog from "../components/BookDialog";

export default function App() {

  const [masterPoint, setMasterPoint] = useState([]);

  const [userState, setUserState] = React.useState({
    isAuthenticated: false //paizw m auto gia to debugging
  });

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const [openBookDialog, setOpenBookDialog] = React.useState(false);
  const toggleBookDialog = () => {
    setOpenBookDialog(!openBookDialog);
  };

  const toggleLogout = () => {
    setUserState({});
  };
  
  const [openLogin, setOpenLogin] = React.useState(false);
  const toggleLogin = () => {
    setOpenLogin(!openLogin);
  };

  const handleClose = () => {
    setOpenBookDialog(false);
    setOpenLogin(false);
  };

  

  return (
      <div>
            <Navbar 
            userState={userState}
            setUserState={setUserState}
            open={open}
            bookDialogeClick={toggleBookDialog}
            logoutClick={toggleLogout}
            loginClick={toggleLogin}
            masterPoint={masterPoint} //create state of parent component --- instead of child controlling the state, the parent will
            setMasterPoint={setMasterPoint} //create state of parent component --- instead of child controlling the state, the parent will
            />
        
            {userState.isAuthenticated ? ( 
              <BookDialog
                open={openBookDialog} handleClickOpen={handleClickOpen} handleClose={handleClose} userState={userState} 
                setUserState={setUserState}
              />
            ) : null}
            <DialogeLogin open={openLogin} handleClickOpen={handleClickOpen} handleClose={handleClose} userState={userState} setUserState={setUserState}/>
            <Map masterPoint={masterPoint} setMasterPoint={setMasterPoint} userState={userState} setUserState={setUserState}/>
      </div>
      
  );
}


