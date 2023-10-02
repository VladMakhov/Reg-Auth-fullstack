import Link from 'next/link'
import Layout from '../components/layout'

export default function Home() {
  return (
    <div>
      <Layout>
        <h1>Home</h1>
        <p><Link href="/signin">Sign In</Link></p>
        <p><Link href="/signup">Registration</Link></p>
      </Layout>
    </div>
  )
}
