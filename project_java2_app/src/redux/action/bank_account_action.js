import {createSlice} from "@reduxjs/toolkit"

const bank_account_action = createSlice({
        name: "bank_account",
        initialState : {
            bank_acc: {
                data: null,
                isFetching: false,
                error: false
            }
        },

        reducers: {

            getBankAccountUserStart: (state) => {
                state.bank_acc.isFetching = true;
            },
            getBankAccountUserSuccess: (state, action) => {
                state.bank_acc.isFetching = false;
                state.bank_acc.data = action.payload;
                state.bank_acc.error = false;
            },
            getBankAccountUserFailed: (state) => {
                state.bank_acc.isFetching = false;
                state.bank_acc.error = true;
            }
        }
    }
);

export const {
    getBankAccountUserStart,
    getBankAccountUserSuccess,
    getBankAccountUserFailed
} = bank_account_action.actions;

export default bank_account_action.reducer;