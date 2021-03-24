import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import CloseIcon from '@material-ui/icons/Close';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles'; 
import styled from 'styled-components';
import Divider from '@material-ui/core/Divider';

import OutlinedInput from '@material-ui/core/OutlinedInput';
import InputLabel from '@material-ui/core/InputLabel';
import InputAdornment from '@material-ui/core/InputAdornment';
import FormControl from '@material-ui/core/FormControl';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

import axios from 'axios';

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

const Div = styled.div`
    text-align: center;
    color: #e53935;
    font-family: Arial, Helvetica, sans-serif;
    `;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'center'
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing.unit / 2,
    top: theme.spacing.unit / 2,
    color: theme.palette.grey[900],
  },
}));

export default function DialogeLogin(props) {

  const classes = useStyles();

  const [form, setForm] = useState({
    username: '',
    password: '',
    userid: ''
  });

  const handleInputChange = e => {
    const { id, value } = e.target;
    setForm({...form, [id]: value});
    //console.log('This is my form', form);

  }

  const handlePostData = () => {
    console.log(form);
    //console.log('test1');
    //const body = JSON.stringify(form);
    const params = new URLSearchParams();
    params.append('username', form.username);
    params.append('password', form.password);
    const url = "https://localhost:8765/evcharge/api/login";
    const url2 = "https://localhost:8765/web/users/" + form.username;

    fetch(url2)
      .then(response => response.json())
      .then(data => {
        form.userid = data.userid
        //console.log(form.userid)
      })

    axios
      .post(url, params, {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      })
      .then(res => {
        //console.log(res.data);
        //console.log('test2');
        const formCopy = {...form}
        localStorage.setItem('token', res.data.token);
        formCopy.isAuthenticated = true
        //formCopy.userid = res.data.userid
        setForm({})
        props.setUserState(formCopy)
        console.log(res.data.token);
        //console.log(formCopy.username)
        //console.log(formCopy.userid)
        props.handleClose();
      })
      .catch(function (error) {
        setForm({...form, ['response']: true})
        //console.log(error.res);
        //console.log("error testing");
      });
    };


  /* useEffect(() => {
      console.log('test')
      console.log(form.username)
      const url = "http://localhost:8765/web/users/" + form.username;
      
      fetch(url)
      .then(response => response.json())
        .then(data => {
          
          const formCopy = {...form}
          formCopy.userid = data.userid
          setForm({})
          props.setUserState(formCopy)
          props.handleClose();
        });
        console.log('kappa')
        console.log(form.userid)    
  }, []);  */

    // INPUT VALIDATION

    const handleExitUsername = e => {
      const { value } = e.target;
      const em = validateUsername(value);
  
      function validateUsername(username) {
        //var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        const re = /^\S*$/;
        return re.test(String(username).toLowerCase());
      }
  
      if (!em && value !== "") {
        setForm({ ...form, ["errorUsername"]: true, ["helperTextUsername"]: "Invalid Username" })
      } else if (value === "") {
        setForm({ ...form, ["errorUsername"]: true, ["helperTextUsername"]: "Username field is empty" })
      } else {
        setForm({ ...form, ["errorUsername"]: false, ["helperTextUsername"]: "" })
      }
    }
  
    const handleExitPassword = e => {
      const { value } = e.target;
      const pw = validatePassword(value);
  
      function validatePassword(password) {
        //var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        const re = /^\S*$/;
        return re.test(String(password).toLowerCase());
      }
  
      if (!pw && value !== "") {
        setForm({ ...form, ["errorPassword"]: true, ["helperTextPassword"]: "Password cannot contain special characteres (@#!$%ˆ&*), must be 8 characters or longer, at least one letter and one number" })
      } else if (value === "") {
        setForm({ ...form, ["errorPassword"]: true, ["helperTextPassword"]: "Password is empty" })
      } else {
        setForm({ ...form, ["errorPassword"]: false, ["helperTextPassword"]: "" })
      }
    }

    function ResponseBackend() {
      if (form.response) {
        return <h3> Invalid username or password </h3>
      } else {
        return null
      }
    } 

    
  
    const [values, setValues] = React.useState({
      password: '',
      showPassword: false,
    });

    const handleClickShowPassword = () => {
      setValues({ ...values, showPassword: !values.showPassword });
    };
  
    const handleMouseDownPassword = event => {
      event.preventDefault();
    };

  return (
    <div>
      <Dialog 
        open={props.open} 
        onClose={props.handleClose} 
        aria-labelledby="form-dialog-title"
     >
        <DialogTitle id="form-dialog-title">Login</DialogTitle>
        <IconButton aria-label="Close" className={classes.closeButton} onClick={props.handleClose}>
          <CloseIcon />
        </IconButton>
        <DialogContent>
          <DialogContentText > Welcome back! <br/> Please, login first to start charging! </DialogContentText>
            <Divider /><br /><ThemeProvider theme={theme}>
          <TextField
            autoFocus
            variant="outlined"
            style = {{width: 350}}
            // margin="dense"
            id="username"
            label="Username"
            type="text"
            fullWidth
            error={form.errorUsername}
            helperText={form.helperTextUsername}
            value={form.username}
            onChange={handleInputChange}
            onBlur={handleExitUsername}
          />
          </ThemeProvider>
          <br />
          <br />
          <div >
          <FormControl  style = {{width: 350}} className={classes.margin} variant="outlined">
            <InputLabel>Password</InputLabel>
            <OutlinedInput
              // style = {{width: 500}}
              // margin="dense"
              required 
              // fullWidth
              // className={classes.margin}         
              id="password"
              // label="Password"
              type={values.showPassword ? 'text' : 'password'}
              error={form.errorPassword}
              helperText={form.helperTextPassword}
              value={form.password}
              onChange={handleInputChange}
              onBlur={handleExitPassword}
              endAdornment={
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                    onMouseDown={handleMouseDownPassword}
                    edge="end"
                  >
                    {values.showPassword ? <Visibility /> : <VisibilityOff />}
                  </IconButton>
                </InputAdornment>
              }
              labelWidth={100}
            />
            </FormControl>
          </div>
        </DialogContent>
        <Div>
        <ResponseBackend />
        </Div>
        <DialogActions style={{ marginRight: 10 }}>
        <ThemeProvider theme={theme}>
        <Button onClick={props.handleClose} color="primary">
              Cancel
          </Button>
            <Button onClick={handlePostData} color="primary">
              Login
          </Button>
          </ThemeProvider>
        </DialogActions>
      </Dialog>
    </div>
  );
}