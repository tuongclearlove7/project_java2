import React, {useEffect, useState} from 'react';
import {Link, NavLink} from "react-router-dom";
import Logout from "../auth/Logout";
import {useSelector} from "react-redux";
import Unauthorized from "./Unauthorized";
import {jwtDecode} from "jwt-decode";
import list_nav from '../element/routes';
import DropDown from "./DropDown";

const Header = () => {


    const [show, setShow] = useState(true);
    const user = useSelector((state) => state.auth.login.currentUser);

    return (
        <div>
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <NavLink className="navbar-brand" to={"/"} >
                        React app
                    </NavLink>
                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"/>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            {list_nav?.routes.map((route, index) => {
                                if (route.isProtected && !user) return null;
                                return (
                                    <span key={index} >
                                        <li className="nav-item">
                                            <NavLink className="nav-link" aria-current="page" to={route.path}>
                                                {route.name}
                                            </NavLink>
                                        </li>
                                    </span>
                                );
                            })}
                            {list_nav?.dropdowns.map((dropdown, index) => {
                                if (dropdown.isProtected && !user) return null;
                                return (
                                    <span key={index} >
                                        {<DropDown/>}
                                    </span>
                                );
                            })}
                            <li className="nav-item">
                                <NavLink className="nav-link" aria-current="page" to={"/login"}>
                                    Login
                                </NavLink>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            <input
                                className="form-control me-2"
                                type="search"
                                placeholder="Search"
                                aria-label="Search"
                            />
                            <button className="btn btn-outline-success" type="submit">
                                Search
                            </button>
                        </form>
                        {user && (<Logout></Logout>)}

                    </div>
                </div>
            </nav>
        </div>

    );
};

export default Header;