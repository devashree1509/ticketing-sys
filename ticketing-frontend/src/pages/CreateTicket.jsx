import React, { useState } from "react";
import axiosInstance from "../api/axios";
import { useNavigate } from "react-router-dom";

function CreateTicket(){
    const[title,setTitle]=useState("");
    const[description,setDescription]=useState("");
    const[priority,setPriority]=useState("LOW");

    const navigate = useNavigate();

    const handleSubmit = async(e) =>{
        e.preventDefault();

        if(!title || !description){
            alert("All fields are required");
            return;
            }
        try{
            await axiosInstance.post("/tickets",{
                title,
                description,
                priority
                });
            alert("Ticket created");

            navigate("/tickets");
            }
        catch(err){
            console.log(err);
            alert("Error in Creating ticket");
            }
         }
    return(
        <div>
            <h2>Create Ticket</h2>
            <form onSubmit={handleSubmit}>
            <input
            type="text"
            placeholder="Enter title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            />
            <br /><br />

            <textarea
            placeholder="Enter Description"
            value={description}
            onChange={(e)=> setDescription(e.target.value)}
            />
            <br /> <br />

            <select
            value={priority}
            onChange={(e)=> setPriority(e.target.value)}
            >
            <option value="LOW">LOW</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="HIGH">HIGH</option>
            </select>
            <br /> <br />

            <button type="submit">Create Ticket</button>
            </form>
        </div>
        );
}

export default CreateTicket;