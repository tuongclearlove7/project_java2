import logo from './logo.svg';
import './App.css';
import Header from "./components/element/Header";
import {BrowserRouter, Route, Routes, Outlet} from 'react-router-dom';
import Footer from "./components/element/Footer";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function App() {


  return (
      <div className="App">
          <div className={"container"}>
              <div className={"header-app"}>
                  <Header/>
              </div>
              <div className={"content-app"}>
                  <Outlet></Outlet>
              </div>
              <div className={"footer-app"}>
                  <Footer/>
              </div>
          </div>
          <ToastContainer />
      </div>
  );
}

export default App;
