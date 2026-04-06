import React,{ useEffect, useState} from "react";
import axiosInstance from "../api/axios";
import { useParams } from "react-router-dom";
import { getUserRole } from "../utils/auth";

function TicketDetail(){
    const { id }= useParams();

    const[ticket, setTicket] = useState(null);
    const[comments, setComments] = useState([]);
    const[newComment, setNewComment] = useState("");
    const[status, setStatus] = useState("");
    const[agentId, setAgentId]=useState("");
    const role=getUserRole();

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
            setComments(res.data.data);
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

        try{
            await axiosInstance.post(`/tickets/${id}/comments`,{
                content: newComment,
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
            }
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

               {(role === "ADMIN" || role === "AGENT") &&(
                   <div>
                       <h3>Update Status</h3>

                       <select onChange={(e) => setStatus(e.target.value)}>
                           <option value="OPEN">OPEN</option>
                           <option value="IN_PROGRESS">IN_PROGRESS</option>
                           <option value="RESOLVED">RESOLVED</option>
                           </select>

                           <button onClick={handleStatusUpdate}>Update Status</button>
                           </div>
                       )}

                       { role === "ADMIN" &&(
                           <div>
                       <h3>Assign Ticket</h3>
                       <input type="text"
                       placeholder="Enter Agent id "
                       onChange={(e)=> setAgentId(e.target.value)}/>

                       <button onClick={handleAssign}>Assign</button>
                       </div>
                   )}

                <h3>Comments</h3>
                {comments && comments.map((c)=>(
                    <div key={c.id}>
                        <p>{c.content}</p>
                        </div>
                       ))}

                   <input
                   type="text"
                   placeholder="Add Comment"
                   value={newComment}
                   onChange={(e)=> setNewComment(e.target.value)}
                   />

                   <button onClick={handleAddComment}>Add</button>
                   </div>

        );
 }

export default TicketDetail;