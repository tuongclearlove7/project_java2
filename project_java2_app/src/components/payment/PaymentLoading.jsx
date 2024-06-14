import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getPaymentSuccess} from "../../redux/action/bank_account_action";
import bill_image from '../../assets/img/bill.png';
import {notify} from "../../api/api";

const PaymentLoading = () => {

    const dispatch = useDispatch();
    const payment_data = useSelector((state) =>
    state.bank_account.payment?.data);

    useEffect(() => {
        return () => {
            dispatch(getPaymentSuccess(null));
        }
    }, []);

    return (
        <div className="col-xl-4">
            <div className="card checkout-order-summary">
                <div className="card-body">
                    <div className="p-3 bg-light mb-3">
                        <h5 className="font-size-16 mb-0 text-success">
                            YOUR PAYMENT INVOICE
                        </h5>
                    </div>
                    <div className="table-responsive">
                        {/*Loading payment data*/}
                        <table className="table table-centered mb-0 table-nowrap">
                            <thead>
                            <tr>
                                <th className="border-top-0"
                                    style={{width: 110}}
                                    scope="col">
                                    Payment image
                                </th>
                                <th className="border-top-0" scope="col">
                                    Image name
                                </th>
                                <th className="border-top-0" scope="col">
                                    Type
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">
                                    <img
                                        src={bill_image}
                                        alt="product-img"
                                        title="product-img"
                                        className="avatar-lg rounded"
                                    />
                                </th>
                                <td>
                                    <h5 className="font-size-16 text-truncate">
                                        <a href="#" className="text-dark">
                                            Smartphone Dual Camera
                                        </a>
                                    </h5>
                                    <p className="text-muted mb-0">
                                        <i className="bx bxs-star text-warning"/>
                                        <i className="bx bxs-star text-warning"/>
                                        <i className="bx bxs-star text-warning"/>
                                        <i className="bx bxs-star text-warning"/>
                                    </p>
                                </td>
                                <td>$ 260</td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    <h6 className="font-size-14 m-0">
                                        Your bank code : {' '}
                                        {payment_data?.payment_status &&
                                        payment_data ? payment_data?.user_payment?.user_bank_code :
                                            <span>
                                               {payment_data  ? " None" : " None"}
                                           </span>
                                        }
                                    </h6>
                                </td>
                                <td>

                                </td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    <h6 className="font-size-14 m-0">
                                        Start time : {' '}
                                        {payment_data?.payment_status &&
                                        payment_data ? payment_data?.user_payment?.start_time :
                                            <span>
                                               {payment_data  ? " None" : " None"}
                                           </span>
                                        }
                                    </h6>
                                </td>
                                <td>

                                </td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    <h6 className="font-size-14 m-0">
                                        Deposit amount :
                                        {payment_data?.payment_status &&
                                        payment_data ? ` +${payment_data?.user_payment?.deposit_amount} VNĐ`
                                        : <span>
                                           {payment_data  ? ` +${0.0} VNĐ` : ` +${0.0} VNĐ`}
                                       </span>
                                        }
                                    </h6>
                                </td>
                                <td>

                                </td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    <h6 className="font-size-14 m-0">
                                        Payment content {' '} :
                                        {payment_data?.payment_status &&
                                        payment_data ?
                                        payment_data?.user_payment?.payment_content :
                                            <span>
                                               {payment_data  ? " None" : " None"}
                                           </span>
                                        }
                                    </h6>
                                </td>
                                <td>

                                </td>
                            </tr>
                            <tr className="bg-light">
                                <td colSpan={2}>
                                    <h6 className="font-size-14 m-0">
                                        Payment status: {' '}
                                        <span className="text-success">
                                           {payment_data?.payment_status &&
                                               payment_data ? payment_data?.message :
                                               <span className="text-danger">
                                                   {payment_data  ?  payment_data?.data?.error
                                                   : " None"}
                                               </span>
                                           }
                                        </span>
                                    </h6>
                                </td>
                                <td>

                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PaymentLoading;