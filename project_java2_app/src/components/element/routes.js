import Home from "../Home/Home";
import View from "../Home/View";
import Login from "../auth/Login";
import Unauthorized from "./Unauthorized";


const routes = [

    {
        name: "Home",
        path: '/home',
        element: <Home />,
        isProtected: true
    },
    {
        name: "View",
        path: '/home/view',
        element: <View />,
        isProtected: true
    },

];

const dropdowns = [
    {
        name: "Dropdown",
        isProtected: true
    },
];

export default {
    routes : routes,
    dropdowns : dropdowns
};
