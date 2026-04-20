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
    const[loading, setLoading]=useState(true);

    console.log(getUserRole());

    const fetchTicket = async () => {
        try{
            const res= await axiosInstance.get(`/tickets/${id}`);
            console.log("TICKET:",res.data);
            if(res.data.success){
            setTicket(res.data.data);
            }else{
                alert(res.data.message);
                }
            }
        catch(err){
            console.log(err);
            }
        finally{
            setLoading(false);
            }
        };

    const fetchComments = async () => {
        try{
            const res= await axiosInstance.get(`/tickets/${id}/comments`);
            setComments(res.data || []);
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
                message: newComment
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
                await axiosInstance.put(`/tickets/${id}/assign?agentId=${ agentId }`);
                alert("Assigned successfully");
                fetchTicket();
                }catch(err){
                    console.log(err);
                    }
                };
        const styles = {
            container:{
                display:"flex",
                justifyContent: "center",
                padding: "30px"
                },
            card:{
                width:"600px",
                backgroundColor:"white",
                padding:"25px",
                borderRadius:"12px",
                boxShadow:"0 4px 12px rgba(0,0,0,0.1)",
                },
            title: {
                marginBottom:"15px",
                color:"#2563eb",
                },
            desc:{
                color: "#4b5563",
                marginBottom:"10px"
                },
            commentBox:{
                backgroundColor:"#f3f4f6",
                padding:"10px",
                borderRadius:"8px",
                marginBottom:"10px",
                },
            input: {
                width:"100%",
                padding:"10px",
                marginTop: "10px",
                borderRadius:"6px",
                border:"1px solid #ccc",
                },
            button:{
                marginTop:"10px",
                backgroundColor:"#2563eb",
                color:"white",
                padding: "8px 14px",
                border:"none",
                borderRadius:"6px",
                cursor:"pointer",
                },
            emptyText:{
                color:"#9ca3af",
                },
            };
         if (loading) return <p style={{textAlign: "center"}}>Loading..</p>;
        return(
            <div style = {styles.container}>
                <div style= {styles.card}>
                    <h2 style={styles.title}>Ticket Detail</h2>
                    {ticket &&(
                        <>
                        <h3>{ticket.title}</h3>
                        <p style = {styles.desc}>{ticket.description}</p>
                        <span style={getStatusStyle(ticket.status)}>
                            {ticket.status}</span>
                        </>
                     )}
                 <hr style = {{ margin: "20px 0"}} />
                 <h3> Comments </h3>
                 {comments?.length === 0 ? (
                     <p style={styles.emptyText}>No comments</p>
                     ) : (comments.map((c) => (
                             <div key={c.id} style={styles.commentBox}>
                                 <strong>{c.author?.email || c.author?.name || "User"}</strong>:{""} {c.message}
                             </div>
                             ))
                         )}
                 <textarea
                 placeholder="Add comment.."
                 value={newComment}
                 onChange={(e) => setNewComment(e.target.value)}
                 style={styles.input}
                 />
                 <button style = {styles.button} onClick = {handleAddComment}> Send </button>
                 <hr style = {{ margin: "20px 0"}}/>

                 {(role === "ADMIN" || role === "AGENT")&& (
                     <div>
                         <h3>Update Status</h3>
                         <select
                         style={getStatusStyle.input}
                         onChange={(e) => setStatus(e.target.value)}>
                         <option value = "OPEN">OPEN</option>
                         <option value="IN_PROGRESS">IN_PROGRESS</option>
                         <option value="RESOLVED">RESOLVED</option>
                         <option value="CLOSED">CLOSED</option>
                       </select>

                       <button style={styles.button} onClick={handleStatusUpdate}>Update Status</button>
                       </div>
                     )}

                 {role === "ADMIN" &&(
                     <div>
                         <h3>Assign Ticket</h3>
                         <input
                         type="text"
                         placeholder="Enter Agent ID:"
                         onChange={(e) => setAgentId(e.target.value)}
                         style={styles.input}/>

                     <button style={styles.button} onClick={handleAssign}>Assign</button>
                     </div>
                    )}
              </div>
           </div>
        );
    }
    function getStatusStyle(status){
        if(status === "OPEN"){
            return{backgroundColor: "#f59e0b", color:"white", padding: "4px 10px", borderRadius:"6px"};
            }
       if(status === "IN_PROGRESS"){
           return{backgroundColor: "#3b82f6", color:"white", padding: "4px 10px", borderRadius:"6px"};
          }
       if(status === "RESOLVED"){
           return{backgroundColor: "#16a34a", color:"white", padding: "4px 10px", borderRadius:"6px"};
           }
       return{backgroundColor: "#6b7280", color:"white", padding: "4px 10px", borderRadius:"6px"};}

export default TicketDetail;