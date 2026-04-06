import React from "react";
import { useNavigate } from "react-router-dom";


function Dashboard() {

    const navigate = useNavigate();

    return(
        <div><h2>Dashboard</h2>
        <p>Welcome to Ticketing System</p>

        <button onClick={() => navigate("/tickets")}>
        View Tickets</button>
        </div>
    );
    
}
export default Dashboard;