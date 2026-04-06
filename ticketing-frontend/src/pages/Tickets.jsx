import React,{ useEffect,useState} from "react";
import axiosInstance from "../api/axios";
import { Link } from "react-router-dom";
import { useSearchParams } from "react-router-dom";

function Tickets(){
    const[tickets,setTickets]=useState([]);
    const[searchParams,setSearchParams] = useSearchParams();
    const search = searchParams.get("search") || "";
    const status = searchParams.get("status") || "";


    const fetchTickets = async () => {
        try{
            const res = await axiosInstance.get("/tickets",{
                params:{search:search,status:status,},
                });
            console.log(res.data);
            setTickets(res.data.data.content);
            } catch(err){
                console.log(err);
                }
            };

        useEffect(() => {
            fetchTickets();
            }, [search,status]);

        return (
           <div>
             <h2>Ticket List</h2>

             <input
             type="text"
             placeholder="Search tickets.."
             value={search}
             onChange = {(e) => {
                 setSearchParams({search: e.target.value, status});
                 }}
             />

             <select
             value={status}
             onChange={(e) => {setSearchParams({search, status: e.target.value});
             }}
            style={{marginLeft: "10px"}}>

            <option value="">All</option>
            <option value="OPEN">OPEN</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="RESOLVED">RESOLVED</option>
        </select>

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