import React from "react";
import {BrowserRouter,Routes,Route} from "react-router-dom";

import Login from "./pages/Login.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Tickets from "./pages/Tickets.jsx";
import CreateTicket from "./pages/CreateTicket.jsx"
import TicketDetail from "./pages/TicketDetail.jsx"
function App() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/dashboard" element={<Dashboard/>}/>
                <Route path="/tickets" element={<Tickets/>}/>
                <Route path="/tickets/new" element={<CreateTicket/>}/>
                <Route path="/tickets/:id" element={<TicketDetail/>}/>
            </Routes>
        </BrowserRouter>

    );
}

export default App
