const arropa = [
    {
        izena: "Kamiseta beltza",
        prezioa: 10,
        img: "img/Produktuak/kamiseta1.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta txuria",
        prezioa: 10,
        img: "img/Produktuak/kamiseta2.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta gorria",
        prezioa: 10,
        img: "img/Produktuak/kamiseta3.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta urdina",
        prezioa: 10,
        img: "img/Produktuak/kamiseta4.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta marroia",
        prezioa: 10,
        img: "img/Produktuak/kamiseta5.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Bakero urdinak",
        prezioa: 35,
        img: "img/Produktuak/prakak1.png",
        kategoria: "prakak"
    },

    {
        izena: "Txandal beltza",
        prezioa: 35,
        img: "img/Produktuak/prakak2.png",
        kategoria: "prakak"
    },

    {
        izena: "Bakero grisak",
        prezioa: 35,
        img: "img/Produktuak/prakak3.png",
        kategoria: "prakak"
    },

    {
        izena: "Kargo beige",
        prezioa: 35,
        img: "img/Produktuak/prakak4.png",
        kategoria: "prakak"
    },

    {
        izena: "Bakero urdin argia",
        prezioa: 35,
        img: "img/Produktuak/prakak5.png",
        kategoria: "prakak"
    },

    {
        izena: "jaka1",
        prezioa: 25,
        img: "img/Produktuak/jaka1.png",
        kategoria: "jakak"
    },

    {
        izena: "Jaka ",
        prezioa: 25,
        img: "img/Produktuak/jaka2.png",
        kategoria: "jakak"
    },

    {
        izena: "jaka3",
        prezioa: 25,
        img: "img/Produktuak/jaka3.png",
        kategoria: "jakak"
    },

    {
        izena: "Kuerozko jaka beltza",
        prezioa: 25,
        img: "img/Produktuak/jaka4.png",
        kategoria: "jakak"
    },

    {
        izena: "Kuerozko jaka marroia",
        prezioa: 25,
        img: "img/Produktuak/jaka5.png",
        kategoria: "jakak"
    },

    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/Produktuak/alkandora1.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Koadrozko alkandora",
        prezioa: 25,
        img: "img/Produktuak/alkandora2.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Alkandora berdea",
        prezioa: 25,
        img: "img/Produktuak/alkandora3.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Alkandora urdin iluna",
        prezioa: 25,
        img: "img/Produktuak/alkandora4.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Alkandora urdina",
        prezioa: 25,
        img: "img/Produktuak/alkandora5.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Sudadera txuria",
        prezioa: 25,
        img: "img/Produktuak/sudadera1.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera beltza",
        prezioa: 25,
        img: "img/Produktuak/sudadera2.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera grisa",
        prezioa: 25,
        img: "img/Produktuak/sudadera3.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera urdina",
        prezioa: 25,
        img: "img/Produktuak/sudadera4.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera oria",
        prezioa: 25,
        img: "img/Produktuak/sudadera5.png",
        kategoria: "sudaderak"
    },
]

const produktuakBistaratu = (bistaratzekoProduktuak) => {
    const kontenidoa = document.getElementById("kontenidoa");

    kontenidoa.innerHTML = ""
    bistaratzekoProduktuak.forEach(produktua => {
        const div = document.createElement("div")
        div.className = "produktua"
        div.innerHTML = `
      <img src="${produktua.img}" alt="produktuArgazkia">
      <h3>${produktua.izena}</h3>
      <p class="Prezioa"> $ ${produktua.prezioa}</p>
      <button>Karritora gehitu<img src="img/carro.png"></button>
    `
    kontenidoa.append(div)
    });
}

const filtratuProduktuak = (kategoria) => {
    const bistaratzekoProduktuak = arropa.filter(produktua => produktua.kategoria === kategoria)
    produktuakBistaratu(bistaratzekoProduktuak)

}

const prakakBtn = document.getElementById('prakak');
const kamisetakBtn = document.getElementById('kamisetak');
const sudaderaBtn = document.getElementById('sudaderak');
const alkandorakBtn = document.getElementById('alkandorak');
const jakakBtn = document.getElementById('jakak');
const guztiakBtn = document.getElementById('guztiak');

prakakBtn.addEventListener('click', () => {
    filtratuProduktuak('prakak');
});

kamisetakBtn.addEventListener('click', () => {
    filtratuProduktuak('kamisetak');
});

sudaderaBtn.addEventListener('click', () => {
    filtratuProduktuak('sudaderak');
});

alkandorakBtn.addEventListener('click', () => {
    filtratuProduktuak('alkandorak');
});

jakakBtn.addEventListener('click', () => {
    filtratuProduktuak('jakak');
});

guztiakBtn.addEventListener('click', () => {
    produktuakBistaratu(arropa);
});

produktuakBistaratu(arropa)

function goraBueltatu() {
   
    const goraBueltatu = document.getElementById("goraBueltatu");

    window.addEventListener("scroll", () => {
        goraBueltatu.style.display = window.scrollY > 200 ? "block" : "none";
    });

    goraBueltatu.addEventListener("click", () => {
        window.scrollTo({ top: 0, behavior: "smooth" });
    });
}

goraBueltatu();
