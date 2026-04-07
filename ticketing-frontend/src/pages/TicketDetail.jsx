import React,{ useEffect, useState} from "react";
import axiosInstance from "../api/axios";
import { useParams } from "react-router-dom";
import { getUserRole } from "../utils/auth";

function TicketDetail(){
    const { id }= useParams();
    const role=getUserRole();
    const[ticket, setTicket] = useState(null);
    const[comments, setComments] = useState([]);
    const[newComment, setNewComment] = useState("");
    const[status, setStatus] = useState("");
    const[agentId, setAgentId]=useState("");

    console.log(getUserRole());

    const fetchTicket = async () => {
        try{
            const res= await axiosInstance.get(`/tickets/${id}`);
            console.log("TICKET:",res.data);
            setTicket(res.data.data);
            }
        catch(err){
            console.log(err);
            }
        };

    const fetchComments = async () => {
        try{
            const res= await axiosInstance.get(`/tickets/${id}/comments`);
            setComments(res.data);
            }catch(err){
                console.log(err);
                }
        };
    useEffect(() => {
        fetchTicket();
        fetchComments();
        },[id]);

    const handleAddComment = async () => {
        if(!newComment.trim()) return;

        const user = JSON.parse(localStorage.getItem("user"));

        try{
            await axiosInstance.post(`/tickets/${id}/comments`,{
                message: newComment,
                userId: user.id
                });
            setNewComment("");
            fetchComments();
            }catch(err){
                console.log(err);
                }
        };

        const handleStatusUpdate = async () => {
            try{
                await axiosInstance.put(`/tickets/${id}/status`,{ status });
                alert("Status Updated");
                fetchTicket();
                 }
             catch(err){
                 console.log(err);
                 }
            };

        const handleAssign = async () =>{
            try{
                await axiosInstance.put(`/tickets/${id}/assign`,{ agentId });
                alert("Assigned successfully");
                fetchTicket();
                }catch(err){
                    console.log(err);
                    }
                };

        return(
            <div>
                <h2>Ticket Detail</h2>
                {ticket && (
                    <div>
                        <h3>{ticket.title}</h3>
                        <p>{ticket.description}</p>
                        <p>Status:{ticket.status}</p>
                    </div>
                    )}
                <br />

                <h3>Comments</h3>

                {comments.length === 0 ?(
                    <p>No comments</p>
                    ):(
                        comments.map((c) => (
                            <div key={c.id}>
                                <strong>{c.author?.name}</strong>: {c.message}
                               </div>
                               ))
                            )}
                        <textarea
                        placeholder="Add comments..."
                        value={newComment}
                        onChange={(e)=>setNewComment(e.target.value)}
                        />

                        <br />

                        <button onClick={handleAddComment}>Send</button>

                        <hr />

                        {(role === "ADMIN" || role === "AGENT") && (
                            <div>
                                <h3>Update status</h3>

                                <select onChange={(e) => setStatus(e.target.value)}>
                                    <option value="OPEN">OPEN</option>
                                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                                    <option value="RESOLVED">RESOLVED</option>
                                    <option value="CLOSED">CLOSED</option>
                                </select>

                                <button onClick={handleStatusUpdate}>Update Status</button>
                                </div>

                            )}
                        {role === "ADMIN" && (
                            <div>
                                <h3>Assign Tickets</h3>

                                <input
                                type="text"
                                placeholder="Enter Agent ID:"
                                onChange={(e) => setAgentId(e.target.value)}
                                />

                                <button onClick={handleAssign}>Assign</button>
                                </div>
                            )}
                        </div>

                    );
                }
export default TicketDetail;