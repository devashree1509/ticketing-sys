import React ,{useState} from "react";

 function Login(){

     const [email,setEmail]=useState("");
     const [password,setPassword]=useState("");

     const handleLogin = async () =>{
         try{
             const response = await fetch("http://localhost:8080/api/test");

             const data = await response.text();

             alert("BAckend response:"+data);

             console.log("Email:", email);
             console.log("Password:", password);

         } catch (e) {
             console.error(e);
             alert("Error in connecting backend");

         }
     };

     return(
         <div>
             <h2>Login page</h2>
             <input
                type="email"
                placeholder="Enter Email"
                value={email}
                onChange={(e)=>setEmail(e.target.value)}
             /><br></br>

             <input
             type="password"
             placeholder="Enter Password"
             value={password}
             onChange={(e)=>setPassword(e.target.value)}
             /> <br></br>
             <button onClick={handleLogin} > Login </button>
         </div>
     );
 }
 export default Login;



