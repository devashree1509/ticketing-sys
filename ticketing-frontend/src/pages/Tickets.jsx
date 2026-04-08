import React,{ useEffect,useState} from "react";
import axiosInstance from "../api/axios";
import { Link } from "react-router-dom";
import { useSearchParams } from "react-router-dom";

function Tickets(){
    const[tickets,setTickets]=useState([]);
    const[searchParams,setSearchParams] = useSearchParams();
    const user = JSON.parse(localStorage.getItem("user"));
    const search = searchParams.get("search") || "";
    const status = searchParams.get("status") || "";

const fetchTickets = async () => {
    try {
        const res = await axiosInstance.get("/tickets", {
            params: {
                ...(search && { search }),
                ...(status !== "" && { status }),
            }
        });

        if (res.data.success) {
            setTickets(res.data.data.content);
        } else {
            alert(res.data.message);
        }

    } catch (err) {
        console.log(err);
    }
};
        const handleDelete = async (id) =>{
            const confirmDelete = window.confirm("Are you sure you want to Delete??");
            if(!confirmDelete) return;
            try{
                const res = await axiosInstance.delete(`/tickets/${id}`);
                if(res.data.success){
                    alert(res.data.message);
                    fetchTickets();
                    }
                else{
                    alert(res.data.message);
                    }
                }catch(err){
                    console.log(err);
                    alert("Delete failed");
                    }
            };

        useEffect(() => {
            fetchTickets();
            }, [search,status]);

        return (
           <div>
             <h2>Ticket List</h2>

             {user?.role === "CUSTOMER" && (
                 <Link to="/tickets/new">
                 <button>Create Ticket</button>
                 </Link>)}

             <input
             type="text"
             placeholder="Search tickets.."
             value={search}
             onChange = {(e) => {
                 setSearchParams({
                     ...(e.target.value && { search: e.target.value}),
                     ...(status && {status}),
                     });
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
                                    <th>Action</th>
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
                                        <td><button onClick={() => handleDelete(ticket.id)}>Delete</button>
                                            </td>
                                        </tr>
                                        ))}
                                    </tbody>
                                    </table>
                                    )}
                                </div>
                                );
                            }
       export default Tickets;