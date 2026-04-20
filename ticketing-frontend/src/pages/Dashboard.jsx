import React from "react";
import { useState,useEffect } from "react";
import TicketCard from "../components/TicketCard";
 import axiosInstance from "../api/axios";


function Dashboard() {
    const[tickets, setTickets]=useState([]);
    const[loading, setLoading]=useState(true);

    const user = JSON.parse(localStorage.getItem("user"));

        useEffect(() => {
            fetchTickets();
            },[]);

        const fetchTickets = async () => {

        try{
            const res = await axiosInstance.get("/tickets");
            let allTickets = [];
            if(res.data && res.data.data && res.data.data.content){
                allTickets=res.data.data.content;
                }else if(Array.isArray(res.data)){
                     allTickets=res.data;
                } setTickets(allTickets);
            } catch(err){
                console.error("Error fetching tickets:",err);
                setTickets([]);
                }
            finally{
                    setLoading(false);
                 }
        };
        if(loading){
            return <p style={styles.centerText}>Loading..</p>;
            }
        if(!tickets || tickets.length === 0){
            return <p style={styles.centerText}>No Tickets Found</p>;
            }
        return(
            <div style={styles.container}>
                <h2 style={styles.heading}>My Tickets</h2>

            <div style={styles.grid}>
                {tickets.map((ticket) => (
                    <TicketCard key={ticket.id} ticket={ticket}/>
           ))}
                </div>
                </div>
            );
        }
    const styles = {
        container: {
            padding: "20px",
            },
        heading: {
            marginBottom: "20px",
            color:"#2563eb",
            },
        grid: {
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
            gap:"20px",
            },
        centerText:{
            textAlign: "center",
            marginTop: "50px",
            color: "#6b7280",
            },
        };
export default Dashboard;