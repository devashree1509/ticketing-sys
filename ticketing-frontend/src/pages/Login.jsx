import React ,{useState} from "react";
import axiosInstance from "../api/axios";

function Login(){
    const[email,setEmail]=useState("");
    const[password,setPassword]=useState("");

    const handleLogin = async (e)=>{
        e.preventDefault();

        try{
            const response=await axiosInstance.post("/auth/login",{
                email,password});

                const token=response.data.token;
                localStorage.setItem("token",token);
                console.log("Login Successfully");
                window.location.href="/dashboard";
                }catch(error){
                    console.error("Login failed",error);
                    }
                };
            return(
                <form onSubmit={handleLogin}>
                    <input
                    type="email"
                    placeholder="Enter Email="
                    onChange={(e)=>setEmail(e.target.value)}/>

                    <input
                    type="password"
                    placeholder="Enter Password="
                    onChange={(e)=>setPassword(e.target.value)}/>

                    <button type="submit">Login</button>
                    </form>
                    );
                }
    export default Login;