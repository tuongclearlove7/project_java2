import axios from "axios"
import Cookies from "js-cookie";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {loginStart, loginSuccess, loginFailed} from '../redux/action/auth_action';
import {useSelector} from "react-redux";

export const api = axios.create({
    baseURL:"http://localhost:8080"
});

export const notify = (text, time)=>{
    toast.error(text, {

        position: "top-center",
        autoClose: time,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",

    })
}

export async function loginUser(data, dispatch, navigate){

    dispatch(loginStart());
    try{
        const res = await api.post("/api/v1/login", data);
        if(res.status >= 200 && res.status < 300){
            dispatch(loginSuccess(res.data));
            navigate("/");
            await new Promise(resolve => setTimeout(resolve, 1000));
            window.location.reload();
        }
    }catch (error) {
        dispatch(loginFailed());
    }

}

export async function logoutUser(token) {
    try{
        const res = await api.get(`/api/v1/logout`);
        console.log(res.data)
        return res.data;
    }catch (error) {
        throw error;
    }
}


export const getHeader = (token) =>{


    return {
        Authorization: `Bearer ${token}`,
        "Content-Type" : "application/json",
    }
}
export async function getApi(url, token) {
    try{

        const res = await api.get(url,
            {headers: getHeader(token)});

        return res.data;
    }catch (error) {
        throw error;
    }
}
