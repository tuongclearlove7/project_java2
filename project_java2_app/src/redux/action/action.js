import {getApi} from "../../api/api";
import {getUserFailed, getUserStart, getUserSuccess} from "./user_action";

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



