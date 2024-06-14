import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import styles from "./Payment.module.css";
import {fetchBankAccount} from "./fetchData";
import {getBankAccountUserSuccess} from "../../redux/action/bank_account_action";

const PaymentTable = () => {

    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.login?.currentUser);
    const bank_account = useSelector((state) =>
    state.bank_account.bank_acc?.data);

    useEffect(() => {
        fetchBankAccount(user, dispatch).then();
    }, []);

    useEffect(() => {
        return () => {
            dispatch(getBankAccountUserSuccess(null));
        }
    }, [dispatch]);

    return (
        <div>
            {bank_account && bank_account?.payments ?
            <div>
                <div className="d-flex justify-content-start pb-3">
                    <div className="form-inline">
                        <h2 className="text-muted mr-3" htmlFor="order-sort">
                            PAYMENT HISTORY
                        </h2>
                    </div>
                </div>
                <div className="table-responsive">
                    <table className="table table-hover mb-0">
                        <thead>
                        <tr>
                            <th>Payment ID</th>
                            <th>User bank code</th>
                            <th>Start time</th>
                            <th>Deposit amount</th>
                            <th>Payment content</th>
                        </tr>
                        </thead>
                        <tbody>

                        {bank_account?.payments.map((item, index) =>
                            <tr key={index}>
                                <td>
                                    {item.id}
                                </td>
                                <td>
                                    {item.user_bank_code}
                                </td>
                                <td>
                                    {item.start_time}
                                </td>
                                <td>
                                    {item.deposit_amount + ' VNĐ'}
                                </td>
                                <td>
                                    {item.payment_content}
                                </td>
                            </tr>)}

                        </tbody>
                    </table>
                </div>
            </div> :
            <div>
                <h1>BẠN CHƯA CÓ TÀI KHOẢN VÍ ĐIỆN TỬ</h1>
                <div>
                    <button className="btn btn-success">
                        ACTIVATE ACCOUNT E-WALLET
                    </button>
                </div>
            </div>}
        </div>
    );
};

export default PaymentTable;