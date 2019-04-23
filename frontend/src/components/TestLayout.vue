<template>
    <div class="container">
        <div class="sidebar">
            <nav>
                <ul>
                    <li><a href="#">Home</a></li>
                    <li><a href="#">견적 내기</a></li>
                    <li><a href="#">발신 관리</a></li>
                    <li><a href="#">벤더 관리</a></li>
                    <li><a href="#">도구 관리</a></li>
                    <li><a href="#">가격 관리</a></li>
                    <li><a href="#">견적 관리</a></li>
                </ul>
            </nav>
        </div>
        
        <div class="content">
            <section class="hero">
                <div class="hero-content">

                </div>
            </section>
        </div>

        <div class="footer">
            <footer>
                <ul>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                </ul>
            </footer>
        </div>
    </div>
</template>

<script>
export default {
    
}
</script>

<style scoped>
body {
    font-family: 'sans-serif';
    background: #EFF1F7;
}

.content {
    grid-area: content;
    /* background: url(...) */   
}
.sidebar {
    grid-area: sidebar;
    /* background: linear-gradient(to right, rgba(249, 107, 142, 1), rgba(218, 103, 230, 1), rgba(130, 125, 253, 1)); */
    background-color: slategrey;
    justify-content: center;
}

.footer {
    grid-area: footer;
    background: white;
}

.container {
    font-size: 1.5em;
    width: 100%;
    height: 100vh;
    display: grid;
    grid-template-areas: "sidebar" "content" "footer";
    grid-template-columns: 1fr;
    grid-template-rows: 130px 800px;
}

.content, .sidebar, .footer {
    padding: 12m;
}

nav ul {
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: space-between;
    text-align: center;
}
nav li {
    list-style: none;
    padding: 1em 0;
}
nav li a {
    color: white;
    font-weight: 700;
    opacity: 06;
    text-decoration: none;
    transition: 0.3;
}
nav li a:hover {
    opacity: 1
}
.hero {
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
}
.hero img {
    width: 200px;
}
.hero h1 {
    font-size: 2em;
    font-weight: 300;
    color: #373d46;
}
.hero p {
    font-weight: 300;
    line-height: 1.8em;
    color: #98a0ad;
}

.action-btn {
    display: inline-block;
    text-decoration: none;
    color: white;
    font-weight: 700;
    background: #867bfb;
    padding: 0.5em 2em;
    border-radius: 40px;
    margin: 1em 0;
    transition: 0.3s;
}

.action-btn:hover {
    box-shadow: 0 10px 50px rgba(188, 197, 216, 1);
}

footer ul {
    max-width: 640px;
    margin: 2em auto;
    padding: 0;
    text-align: center;
    display: flex;
    flex-direction: row;
}
footer ul li {
    list-style: none;
    align-self: flex-end;
}
footer ul li a {
    text-decoration: none;
    color: #c1c6ce;
}
svg {
    width: 40%;
}

footer p {
    font-size: 0.8em;
}

@media (min-width: 1040px) {
    .container {
        grid-template-areas: "sidebar content" "sidebar footer";
        grid-template-columns: 300px 1fr;
        grid-template-rows: 1fr auto;
    }

    nav ul {
        display: flex;
        justify-content: space-between;
        flex-direction: column;
    }
    .sidebar {
        /* background: linear-gradient(rgba(249, 107, 142, 1), rgba(218, 103, 230, 1), rgba(130, 125, 253, 1) ); */
        background-color: slategrey;
        padding-top: 3em;
    }
    .hero {
        text-align: left;
        margin: 2em auto;
    }
    .hero img {
        width: 250px;
        float: right;
    }

    .hero h1 {
        font-size: 3em;
    }
    .hero p {
        width: 60%;
    }
    footer ul {
        max-width: 960px;
        margin: 0 auto;
        padding: 2em 0;
    }
    svg {
        width: 20%;
    }
}
</style>