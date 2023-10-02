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

    return (
        <div>
            <h1>User: {content}</h1>
            <p>
                <button onClick={logout}>Log out</button>
            </p>
        </div>
    )
}