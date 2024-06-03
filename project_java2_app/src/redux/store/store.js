import {createStore, applyMiddleware} from 'redux';
import allReducers from "../reducers/index";
import {thunk} from "redux-thunk"
import {configureStore, combineReducers} from "@reduxjs/toolkit"
import auth_reducer from "../action/auth_action";
import user_reducer from "../action/user_action";
import {
    persistStore,
    persistReducer,
    FLUSH,
    REHYDRATE,
    PAUSE,
    PERSIST,
    PURGE,
    REGISTER,
} from 'redux-persist'
import storage from 'redux-persist/lib/storage'

const persistConfig = {
    key: 'root',
    version: 1,
    storage,
}
const rootReducer = combineReducers({
    auth : auth_reducer,
    user : user_reducer
});
const persistedReducer = persistReducer(persistConfig, rootReducer)

 export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
            },
        }),
})

export let persistor = persistStore(store);

