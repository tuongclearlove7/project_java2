import {createSlice} from "@reduxjs/toolkit"

const user_action = createSlice({
        name: "user",
        initialState : {
            user : {
                userLogin: null,
                isFetching: false,
                error: false
            }
        },

        reducers: {
            getUserStart : (state)=>{
                state.user.isFetching = true;
            },
            getUserSuccess : (state, action) =>{

                state.user.isFetching = false;
                state.user.userLogin = action.payload;
                state.user.error = false;
            },
            getUserFailed : (state)=>{
                state.user.isFetching = false;
                state.user.error = true;
            },


        }
    }
);

export const {
    getUserStart,
    getUserSuccess,
    getUserFailed,
} = user_action.actions;

export default user_action.reducer;