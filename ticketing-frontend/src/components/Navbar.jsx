import { useNavigate } from "react-router-dom";
import { getUserRole } from "../utils/auth";

function Navbar(){
    const navigate = useNavigate();
    const role = getUserRole();
    const isLoggedIn = !!localStorage.getItem("token");
    const isLoginPage = location.pathname === "/";
    return(
        <div style={navbarStyle}>
            <h2 style={{margin: 0}}> Ticket System </h2>

            {isLoggedIn && !isLoginPage && (
            <div>
                <button style={navBtn} onClick={()=> navigate("/dashboard")}>Dashboard</button>
                {role === "CUSTOMER" && (
                    <button style={navBtn} onClick={() => navigate("/tickets/new")}>Create Ticket</button>
                    )}
               <button style={logOutBtn} onClick={()=>{localStorage.removeItem("token");navigate("/");
                   }}>Log Out </button>
            </div>
            )}
       </div>

      );
  }

const navbarStyle = {
    backgroundColor: "#2563eb",
    color: "white",
    padding: "12px 20px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    boxShadow: "0 2px 6px rgba(0,0,0,0.1)"
    };

const navBtn = {
    marginLeft: "10px",
    backgroundColor: "white",
    color: "#2563eb",
    border: "none",
    padding: "6px 12px",
    borderRadius: "6px",
    cursor: "pointer"
    };

const logOutBtn = {
    ...navBtn,
    backgroundColor: "#dc2626",
    color: "white"
    };

export default Navbar;

