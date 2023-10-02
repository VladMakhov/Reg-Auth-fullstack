import Link from "next/link";
import { useRouter } from "next/router"
import { useState } from "react"

export default function Signin() {
    
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
        const response = await fetch(`http://localhost:8080/api/auth/signin`, {
            method: "POST",
            body: JSON.stringify(state),
            headers: {
                "Content-Type": "application/json"
            }
        })
        if (response.ok) {
            const json = await response.json()
            localStorage.setItem("token", json.token)
            route.push("/")
        } else {
            alert("Bad credentials")
        }
    }

    return (
        <div>
            <h1>Please Log In</h1><br/>
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
            <p><Link href='/signup'>Registration</Link></p>
        </div>
    )
}