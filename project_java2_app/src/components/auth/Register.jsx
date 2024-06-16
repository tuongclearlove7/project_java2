import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {createAccount, loginUser, notify, verifyAccount} from "../../api/api";
import Form from "./Form";
import {registerSuccess} from "../../redux/action/auth_action";

const Register = () => {

    const [register, setRegister] = useState({
        email: "",
        password: ""
    });
    const [token, setToken] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const registerUser = useSelector((state) => state.auth.register.registerUser);

    useEffect(() => {

        return () => dispatch(registerSuccess(null));
    }, []);

    useEffect(() => {
        console.log(registerUser);
    }, []);

    const handleInputChange = (e) =>{

        setRegister({...register, [e.target.name] : e.target.value});
    }
    


    const handleRegister = async (e)=>{

        e.preventDefault();
        await createAccount(register, dispatch);
    }

    return (
        <div>
            <div className="py-3 py-md-5">
                <div className="container">
                    <div className="row justify-content-md-center">
                        <div className="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                            <div className="bg-white p-4 p-md-5 rounded shadow-sm">
                                <div className="row">
                                    <div className="col-12">
                                        <div className="text-center mb-5">
                                            <a href="#!">
                                                <h1>SIGN UP</h1>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                {/*Form handle*/}
                                <Form handleInputChange={handleInputChange}
                                      handleRegister={handleRegister}
                                      register={register}
                                      setToken={setToken}
                                      token={token}
                                />
                                <div className="row">
                                    <div className="col-12">
                                        <hr className="mt-5 mb-4 border-secondary-subtle"/>
                                        <div
                                            className="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-center">
                                            <a href="#!" className="link-secondary text-decoration-none">
                                                Create new account
                                            </a>
                                            <a href="#!" className="link-secondary text-decoration-none">
                                                Forgot password
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Register;