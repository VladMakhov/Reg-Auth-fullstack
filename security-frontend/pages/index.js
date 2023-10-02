import {useRouter} from "next/router"
import {useEffect, useState} from "react"

export default function User() {
    const [content, setContent] = useState(null)
    const route = useRouter()

    useEffect(() => {
        fetchContent()
    }, [])

    function logout() {
        localStorage.removeItem("token")
        route.push("/home")
    }

    async function fetchContent() {
        if (localStorage.getItem("token") == null) {
            route.push('/home')
        }
        const res = await fetch("http://localhost:8080/api/self", {
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
        if (res.ok) {
            const text = await res.text()
            setContent(text)
        }
    }

    const [state, setState] = useState({
        userId: "",
        amount: ""
    })

    function changeState(form) {
        const copy = { ...state }
        copy[form.target.name] = form.target.value
        setState(copy)
    }

    async function handleSubmit() {
        const response = await fetch(`http://localhost:8080/api/exchange`, {
            method: "PUT",
            body: JSON.stringify(state),
            headers: {
                "Content-Type": "application/json"
            }
        })
        if (response.ok) {
            alert("Exchange completed")
        } else {
            alert("Exchange went wrong")
        }
    }

    return (
        <div>
            <h1>User: {content}</h1>
            <input
                name="userId"
                type="text"
                placeholder="User id"
                value={state.userId}
                onChange={changeState}
                autoComplete="off"/><br/><br/>
            <input
                name="amount"
                type="text"
                placeholder="Amount"
                value={state.amount}
                onChange={changeState}
                autoComplete="off"/><br/><br/>
            <button type="submit" onClick={handleSubmit}>submit</button>
            <br/>
            <p>
                <button onClick={logout}>Log out</button>
            </p>
        </div>
    )
}