import React ,{useState} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";


function Login(){

    const[email,setEmail]=useState("");
    const[password,setPassword]=useState("");
    const navigate= useNavigate();

     const container = {
                        height: "100vh",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "#f3f4f6"
                        };

                    const card = {
                        backgroundColor: "white",
                        padding: "30px",
                        borderRadius: "12px",
                        boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                        width: "300px",
                        display: "flex",
                        flexDirection: "column",
                        gap: "12px"
                        };

                    const title = {
                        textAlign: "center",
                        color: "#2563eb",
                        marginBottom: "10px"
                        };

                    const input = {
                        padding: "10px",
                        borderRadius: "6px",
                        border: "1px solid #ccc"
                        };

                    const button = {
                        backgroundColor: "#2563eb",
                        color: "white",
                        padding: "10px",
                        border: "none",
                        borderRadius: "6px",
                        cursor: "pointer",
                        fontWeight: "bold"
                        };

    const handleLogin = async (e) => {
         e.preventDefault();
        try{
            const res=await axios.post("http://localhost:8080/api/auth/login",{
                email,password});

                if(res.data.token){

                localStorage.setItem("token",res.data.token);

                localStorage.setItem("user", JSON.stringify({
                    id: res.data.id,
                    name: res.data.name,
                    role: res.data.role}));

                   alert("Login Successfully");
                   navigate("/dashboard");
                }else{
                     alert("Login failed");
                     }
                }catch(error){
                    console.error("Login failed",error);
                    alert("Invalid credentials");
                    }

                };

            return(
                <div style={container}>
                 <div style={card}>
                     <h2 style={title}>Login</h2>

                    <input
                    type="email"
                    placeholder="Enter Email"
                    value={email}
                    onChange={(e)=>setEmail(e.target.value)}
                    style={input}
                    />

                    <input
                    type="password"
                    placeholder="Enter Password"
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                    style={input}/>

                    <button style={button} onClick={handleLogin}>Login</button>

                </div>
            </div>
);
}

export default Login;