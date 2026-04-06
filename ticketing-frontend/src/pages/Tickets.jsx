import React,{ useEffect,useState} from "react";
import axiosInstance from "../api/axios";
import { Link } from "react-router-dom";

function Tickets(){
    const[tickets,setTickets]=useState([]);


    const fetchTickets = async () => {
        try{
            const res = await axiosInstance.get("/tickets");
            console.log(res.data);
            setTickets(res.data.data.content);
            } catch(err){
                console.log(err);
                }
            };

        useEffect(() => {
            fetchTickets();
            }, []);

        return (
           <div>
             <h2>Ticket List</h2>

            {tickets.length === 0 ? (
                <p>No Tickets found </p>
                    ):(
                        <table border = "1" cellPadding = "10">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>
                                {tickets
                                    .sort((a,b) => a.id - b.id)
                                    .map((ticket)=>(
                                    <tr key={ticket.id}>
                                        <td>{ticket.id}</td>
                                        <td>
                                            <Link to={`/tickets/${ticket.id}`}>
                                            {ticket.title}</Link></td>
                                        <td>{ticket.status}</td>
                                        </tr>
                                        ))}
                                    </tbody>
                                    </table>
                                    )}
                                </div>
                                );
                            }
       export default Tickets;