import Link from "next/link";
import { useRouter } from "next/router"
import { useState } from "react"

export default function Signup() {
    
    const route = useRouter();

    const [state, setState] = useState({
        username: "",
        password: ""
    })

    function changeState(form) {
        const copy = { ...state }
        copy[form.target.name] = form.target.value
        setState(copy)
    }
    
    async function handleSubmit() {
        const response = await fetch(`http://localhost:8080/api/auth/signup`, {
          method: "POST",
          body: JSON.stringify(state),
          headers: {
            "Content-Type": "application/json"
          }
        })
        if (response.ok) {
          alert("User registered success")
          route.push("/signin")
        }
    }

    return (
        <div>
            <h1>Please Sign Up</h1><br/>
            <input 
                name="username"
                type="text" 
                placeholder="Username" 
                value={state.username} 
                onChange={changeState}
                autoComplete="off"/><br/><br/>
            <input 
                name="password"
                type="password" 
                placeholder="Password" 
                value={state.password} 
                onChange={changeState}
                autoComplete="off"/><br/><br/>
            <button type="submit" onClick={handleSubmit}>submit</button><br/>
            <p><Link href="/">Back</Link></p>
        </div>
    )
}