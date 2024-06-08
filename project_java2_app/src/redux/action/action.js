import {getApi} from "../../api/api";
import {getUserFailed, getUserStart, getUserSuccess} from "./user_action";
import {getBankAccountUserStart, getBankAccountUserSuccess, getBankAccountUserFailed} from "./bank_account_action";


export const getUser = async (token, dispatch) => {
    dispatch(getUserStart());
    try {
        const data = await getApi("/api/user/profile", token);
        await dispatch(getUserSuccess(data));
    } catch (err) {
        console.error("error: ", err);
        dispatch(getUserFailed());
    }
};

export const getBankAccount = async (token, dispatch) => {
    dispatch(getBankAccountUserStart());
    try {
        const data = await getApi("/api/user/bank_account", token);

        await dispatch(getBankAccountUserSuccess(data));
    } catch (err) {
        console.error("error: ", err);
        dispatch(getBankAccountUserFailed());
    }
};



