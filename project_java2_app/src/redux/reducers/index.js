import { combineReducers } from "redux";
import reducerArray from "./reducerArray";
import auth_reducer from "../action/auth_action";
import user_reducer from "../action/user_action";


const rootReducers = combineReducers({
    Array: reducerArray,
    auth : auth_reducer,
    user : user_reducer
});

export default rootReducers;