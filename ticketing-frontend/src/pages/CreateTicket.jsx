import React, { useState } from "react";
import axiosInstance from "../api/axios";
import { useNavigate } from "react-router-dom";

function CreateTicket(){
    const[title,setTitle]=useState("");
    const[description,setDescription]=useState("");
    const[priority,setPriority]=useState("LOW");
    const[category,setCategory] = useState("");
    const[loading, setLoading]=useState(false);
    const[message, setMessage]=useState("");

    const navigate = useNavigate();

    const handleSubmit = async(e) => {
        e.preventDefault();

        if(!title.trim() || !description.trim() || !category.trim()){
            alert("All fields are required");
            return;
        }

        setLoading(true);

        try{
            const res = await axiosInstance.post("/tickets",{
                title,
                description,
                priority,
                category
                });

            if(res.data.success){
                setMessage("Ticket created successfully");
                setTimeout(() => navigate("/dashboard"),1000);
                }else{
                alert(res.data.message);
                }
            }
        catch(err){
            console.log(err);
            alert("Error in Creating ticket");
            }
        finally{
            setLoading(false);
            }
         };
    return(
        <div style={styles.container}>
            <form onSubmit = {handleSubmit} style={styles.form}>
                <h2 style = {{ marginBottom:"15px"}}>CreateTicket</h2>
                {message && <p style={styles.message}>{message}</p>}

            <input
            type="text"
            placeholder="Enter title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            style={styles.input}
            />

            <textarea
            placeholder="Enter Description"
            value={description}
            onChange={(e)=> setDescription(e.target.value)}
            style={styles.input}
            />

            <select
            value={priority}
            onChange={(e)=> setPriority(e.target.value)}
            style={styles.input}
            >
            <option value="LOW">LOW</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="HIGH">HIGH</option>
            </select>

            <input
            type="text"
            placeholder="Enter Category"
            value={category}
            onChange={(e)=>setCategory(e.target.value)}
            style={styles.input}/>

            <button type="submit" style={styles.button} disabled = {loading}>
                {loading ? "Creating.." : "Create Ticket"}</button>

            </form>
        </div>
        );
}

const styles = {
    container:{
        display:"flex",
        justifyContent: "center",
        marginTop: "40px",
        },
    form:{
        backgroundColor:"white",
        padding: "25px",
        borderRadius: "10px",
        boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
        width: "350px",
        },
    input:{
        width:"100%",
        padding:"10px",
        margin:"10px 0",
        borderRadius:"6px",
        border:"1px solid #ccc",
        },
    button:{
        width:"100%",
        backgroundColor:"#2563eb",
        color:"white",
        padding:"10px",
        border:"none",
        borderRadius:"6px",
        cursor:"pointer",
        },
    message:{
        textAlign:"center",
        marginBottom: "10px",
        color:"#16a34a",
        },
    };

export default CreateTicket;