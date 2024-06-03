import {createSlice} from "@reduxjs/toolkit"

const auth_action = createSlice({
        name: "auth",
        initialState : {
            login : {
                currentUser: null,
                isFetching: false,
                error: false
            }

       },

        reducers: {
            loginStart : (state)=>{
                state.login.isFetching = true;
            },
            loginSuccess : (state, action) =>{

                state.login.isFetching = false;
                state.login.currentUser = action.payload;
                state.login.error = false;
            },
            loginFailed : (state)=>{
                state.login.isFetching = false;
                state.login.error = true;
            },
            logout: state => {
                state.login.currentUser = null;
            }
        }
    }
);

export const {
    loginStart,
    loginSuccess,
    loginFailed,
    logout
} = auth_action.actions;

export default auth_action.reducer;





