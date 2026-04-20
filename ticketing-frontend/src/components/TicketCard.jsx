import { useNavigate } from "react-router-dom";

function TicketCard({ticket}){
    const navigate = useNavigate();

    if(!ticket) return null;

    return(
      <div style={styles.card} onClick={() => navigate(`/tickets/${ticket.id}`)}>
              <h3 style={styles.title}>{ticket.title}</h3>
              <p style={styles.desc}>{ticket.description || "No description"}</p>
              <div style={styles.footer}>
               <span style={getStatusStyle(ticket.status)}>{ticket.status}</span>
              <span style={getPriorityStyle(ticket.priority)}>{ticket.priority}</span>
             <button style={styles.button} onClick={(e)=>{e.stopPropagation();
                 navigate(`/tickets/${ticket.id}`);
                 }}>
             </button>
         </div>
         </div>
    );
}

const styles = {
    input:{
        width:"100%",
        padding:"10px",
        marginTop:"10px",
        borderRadius:"6px",
        border:"1px solid #ccc"
        },
    card:{
        backgroundColor: "white",
        padding: "15px",
        borderRadius: "10px",
        boxShadow:"0 2px 8px rgba(0,0,0,0.1)",
        cursor: "pointer",
        transition: "0.2s",
        },
    title:{
        marginBottom: "5px",
        },
    desc:{
        color: "#4b5563",
        marginBottom: "10px",
        },
    footer:{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        marginTop: "12px",
        },
    button:{
        backgroundColor: "#2563eb",
        color:"white",
        border:"none",
        padding: "6px 12px",
        borderRadius: "6px",
        cursor: "pointer",
        },
    };

function getPriorityStyle(priority){
    if(priority === "HIGH"){
        return {backgroundColor:"#dc2626", color:"white", padding:"4px 8px", borderRadius:"6px"};
        }
    if(priority === "MEDIUM"){
            return {backgroundColor:"#f59e0b", color:"white", padding:"4px 8px", borderRadius:"6px"};
        }
    if(priority === "LOW"){
            return {backgroundColor:"#16a34a", color:"white", padding:"4px 8px", borderRadius:"6px"};
        }
    return{
        backgroundColor: "#6b7280", color:"white", padding:"4px 8px", borderRadius:"6px"};
        };
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


export default TicketCard;