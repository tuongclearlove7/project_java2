import React, {useEffect, useState} from 'react';
import styles from "./Payment.module.css";
import '../../assets/css/payment.css';
import {useDispatch, useSelector} from "react-redux";
import GeneratePaymentToken from "./GeneratePaymentToken";
import {getPaymentToken} from "../../redux/action/action";
import {paymentPost, notify} from "../../api/api";
import PaymentLoading from "./PaymentLoading";

const Payment = () => {

    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.login?.currentUser);
    const [showToken,setShowToken] = useState(false);
    const [input, setInput] = useState("");
    const [file, setFile] = useState();

    const handleInputChange = (e) =>{
        setInput(e.target.value);
    }

    function handleChooseFileChange(event) {
        setFile(event.target.files[0])
    }

    const handleSubmitPayment = async (e) =>{

        e.preventDefault();
        const form = e.target;
        const user_bank_code = form.elements?.account_number_sent_to.value;
        if(!user_bank_code){
            notify("Vui lòng nhập vào số tài khoản của bạn!",2000);
            return;
        }
        if (!file) {
            notify("LỖI: File không tồn tại!!!",2000)
            console.log("LỖI: File không tồn tại!!!");
            return;
        }
        try{
            await paymentPost("/api/user/user_payment", user?.accessToken,{
                account_number_sent_to : user_bank_code,
                file: file,
            }, dispatch).then(res => notify(res.message, 2000)
            ).catch(err => notify(err, 2000));

        }catch (error) {
            notify(error.response?.data?.error, 2000);
        }
    }

    const handleShowPaymentToken = async ()=>{

        setShowToken(true);
        await new Promise(resolve => setTimeout(resolve, 2000));
        HandlePaymentToken().then(() => {
            setShowToken(false);
        });
    }
    const HandlePaymentToken = async () => {
        try {
            if (user?.accessToken) {
                await getPaymentToken(user?.accessToken, dispatch);
            }
        } catch (error) {
            console.error("Error fetching user data: ", error);
        }
    };

    return (
        <>
            <div className={styles.distance_paymentHistoryTable_top}>
                <div className="row">
                    <div className="col-xl-8">
                        <div className="card">
                            <div className="card-body">
                                <ol className="activity-checkout mb-0 px-4 mt-3">
                                    <li className="checkout-item">
                                        <div className="avatar checkout-icon p-1">
                                            <div className="avatar-title rounded-circle bg-primary">
                                                <i className="bx bxs-receipt text-white font-size-20"/>
                                            </div>
                                        </div>
                                        <div className="feed-item-list">
                                            <div>
                                                <h5 className="font-size-16 mb-1">ELECTRONIC WALLET</h5>
                                                <div className="mb-3">
                                                    <form onSubmit={handleSubmitPayment}>
                                                        <div>
                                                            <div className="row">

                                                                <div className="col-lg-6">
                                                                    <div className="mb-3">
                                                                        <label className="form-label"
                                                                               htmlFor="billing-email-address">
                                                                            Number account send
                                                                        </label>
                                                                        <input type="text"
                                                                               className="form-control"
                                                                               id="account_number_sent_to"
                                                                               name="account_number_sent_to"
                                                                               onChange={handleInputChange}
                                                                               value={input}
                                                                               placeholder="Enter your number account send"
                                                                        />
                                                                    </div>
                                                                </div>
                                                                <div className="col-lg-6">
                                                                    <div className="mb-3">
                                                                        <label className="form-label"
                                                                               htmlFor="billing-phone">
                                                                            Image
                                                                        </label>
                                                                        <input type="file"
                                                                               className="form-control"
                                                                               id="file" name={"file"}
                                                                               onChange={handleChooseFileChange}
                                                                        />
                                                                    </div>
                                                                </div>

                                                                <div className="col">
                                                                    <div className="text-end mt-2 mt-sm-0">
                                                                        <button onClick={() => {
                                                                        }} className="btn btn-success">
                                                                            <i className="mdi mdi-cart-outline me-1"/> Payment
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li className="checkout-item">
                                        <div>
                                        <button onClick={handleShowPaymentToken} className="btn btn-success">
                                                Generate payment token
                                            </button>
                                        </div>
                                    </li>
                                    {/*  Generate payment token */}
                                    {showToken && (
                                        <div className="mb-5">
                                            <h2>Generate payment token...</h2>
                                        </div>
                                    )}
                                    <GeneratePaymentToken/>
                                </ol>

                            </div>
                        </div>
                    </div>
                    {/* end row*/}
                    <PaymentLoading/>
                </div>
                {/* end row */}
            </div>

        </>

    );
};

export default Payment;