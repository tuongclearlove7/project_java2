import { combineReducers } from "redux";
import reducerArray from "./reducerArray";
import auth_action from "../action/auth_action";
import user_action from "../action/user_action";


const allReducers = combineReducers({
    Array: reducerArray,
    Auth : auth_action,
    User: user_action
});

export default allReducers;