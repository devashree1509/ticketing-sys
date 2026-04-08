import React ,{useState} from "react";
import axios from "axios";


function Login(){

    const[email,setEmail]=useState("");
    const[password,setPassword]=useState("");

    const handleLogin = async (e) => {
         e.preventDefault();

        try{
            const res=await axios.post("http://localhost:8080/api/auth/login",{
                email,password});
                if(res.data.success){
                    const data=res.data.data;
                const token = res.data.data.token;
                localStorage.setItem("token",token);
                localStorage.setItem("user", JSON.stringify({
                    id: data.id,
                    name: data.name,
                    role: data.role}));
                alert("Login Successfully");
                 window.location.href="/dashboard";
                 }else{
                     alert("Login failed");
                     }
                }catch(error){
                    console.error("Login failed",error);
                    alert("Invalid credentials");
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

                    <button type="submit">Login</button>
                    </form>
                    );
                }
    export default Login;