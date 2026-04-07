import React ,{useState} from "react";
import axiosInstance from "../api/axios";
import { jwtDecode } from "jwt-decode";

function Login(){
    const[email,setEmail]=useState("");
    const[password,setPassword]=useState("");

    const handleLogin = async (e) => {
         e.preventDefault();
        try{
            const response=await axiosInstance.post("/auth/login",{
                email,password});
                const token = response.data.token;
                localStorage.setItem("token",token);
                localStorage.setItem("user", JSON.stringify({
                    id: response.data.id,
                    name: response.data.name,
                    role: response.data.role}));

                console.log("Login Successfully");
                 window.location.href="/dashboard";
                }catch(error){
                    console.error("Login failed",error);
                    }

                };

            return(
                <form onSubmit={(e)=>{
                    e.preventDefault();
                    handleLogin(e)}}>
                    <input
                    type="email"
                    value={email}
                    placeholder="Enter Email"
                    onChange={(e)=>setEmail(e.target.value)}/>

                    <input
                    type="password"
                    value={password}
                    placeholder="Enter Password"
                    onChange={(e)=>setPassword(e.target.value)}/>

                    <button type="button" onClick={handleLogin}>Login</button>
                    </form>
                    );
                }
    export default Login;