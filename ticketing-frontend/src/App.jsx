import React from "react";
import {BrowserRouter, Routes , Route} from "react-router-dom";

import Login from "./pages/Login.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Tickets from "./pages/Tickets.jsx";
import CreateTicket from "./pages/CreateTicket.jsx"
import TicketDetail from "./pages/TicketDetail.jsx"
import Navbar from "./components/Navbar.jsx";
import "./index.css";

function App() {

    return(
        <div style={{ backgroundColor: "#f9fafb", minHeight: "100vh", width: "100%" }}>
        <BrowserRouter>
              <Navbar />
            <Routes>
                <Route path="/" element={<Login />}/>
                <Route path="/dashboard" element={<Dashboard />}/>
                <Route path="/tickets" element={<Tickets />}/>
                <Route path="/tickets/new" element={<CreateTicket />}/>
                <Route path="/tickets/:id" element={<TicketDetail />}/>
            </Routes>
        </BrowserRouter>
        </div>

    );
}

export default App;
