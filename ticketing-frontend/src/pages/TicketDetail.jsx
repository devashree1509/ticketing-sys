import React,{ useEffect, useState} from "react";
import axiosInstance from "../api/axios";
import { useParams } from "react-router-dom";

function TicketDetail(){
    const { id }= useParams();

    const[ticket, setTicket] = useState(null);
    const[comments, setComments] = useState([]);
    const[newComment, setNewComment] = useState("");

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