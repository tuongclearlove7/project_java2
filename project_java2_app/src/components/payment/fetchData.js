import {getBankAccount, getUser} from "../../redux/action/action";
import {
    getPaymentTokenFailed,
    getPaymentTokenStart,
    getPaymentTokenSuccess
} from "../../redux/action/bank_account_action";
import {getApi} from "../../api/api";
export const fetchBankAccount = async (user, dispatch) => {
    try {
        if (user?.accessToken) {
            await getBankAccount(user.accessToken, dispatch);
        }
    } catch (error) {
        console.error("Error fetching user data: ", error);
    }
};

export const  fetchPaymentToken = async (token, dispatch)=>{
    dispatch(getPaymentTokenStart());
    try{
        if(token){
            getApi("/api/user/get_payment_token",token).then(
                res=>{
                    dispatch(getPaymentTokenSuccess(res));
                }
            ).catch(error=>{
                dispatch(getPaymentTokenFailed({
                    status: error.response?.status,
                    statusText: error.response?.statusText,
                    data: error.response?.data,
                }));
            });
        }
    }catch (error) {
        dispatch(getPaymentTokenFailed({
            status: error.response?.status,
            statusText: error.response?.statusText,
            data: error.response?.data,
        }));
    }
}