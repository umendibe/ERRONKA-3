const arropa = [
    {
        izena: "kamiseta1",
        prezioa: 10,
        img: "img/Produktuak/kamiseta1.png",
        kategoria: "kamisetak"
    },

    {
        izena: "kamiseta2",
        prezioa: 10,
        img: "img/Produktuak/kamiseta2.png",
        kategoria: "kamisetak"
    },

    {
        izena: "kamiseta3",
        prezioa: 10,
        img: "img/Produktuak/kamiseta3.png",
        kategoria: "kamisetak"
    },

    {
        izena: "kamiseta4",
        prezioa: 10,
        img: "img/Produktuak/kamiseta4.png",
        kategoria: "kamisetak"
    },

    {
        izena: "kamiseta5",
        prezioa: 10,
        img: "img/Produktuak/kamiseta5.png",
        kategoria: "kamisetak"
    },

    {
        izena: "prakak1",
        prezioa: 35,
        img: "img/Produktuak/prakak1.png",
        kategoria: "prakak"
    },

    {
        izena: "prakak2",
        prezioa: 35,
        img: "img/Produktuak/prakak2.png",
        kategoria: "prakak"
    },

    {
        izena: "prakak3",
        prezioa: 35,
        img: "img/Produktuak/prakak3.png",
        kategoria: "prakak"
    },

    {
        izena: "prakak4",
        prezioa: 35,
        img: "img/Produktuak/prakak4.png",
        kategoria: "prakak"
    },

    {
        izena: "prakak5",
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
        izena: "jaka2",
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
        izena: "jaka4",
        prezioa: 25,
        img: "img/Produktuak/jaka4.png",
        kategoria: "jakak"
    },

    {
        izena: "jaka5",
        prezioa: 25,
        img: "img/Produktuak/jaka5.png",
        kategoria: "jakak"
    },

    {
        izena: "alkandora1",
        prezioa: 25,
        img: "img/Produktuak/alkandora1.png",
        kategoria: "alkandorak"
    },

    {
        izena: "alkandora2",
        prezioa: 25,
        img: "img/Produktuak/alkandora2.png",
        kategoria: "alkandorak"
    },

    {
        izena: "alkandora3",
        prezioa: 25,
        img: "img/Produktuak/alkandora3.png",
        kategoria: "alkandorak"
    },

    {
        izena: "alkandora4",
        prezioa: 25,
        img: "img/Produktuak/alkandora4.png",
        kategoria: "alkandorak"
    },

    {
        izena: "alkandora5",
        prezioa: 25,
        img: "img/Produktuak/alkandora5.png",
        kategoria: "alkandorak"
    },

    {
        izena: "sudadera1",
        prezioa: 25,
        img: "img/Produktuak/sudadera1.png",
        kategoria: "sudaderak"
    },

    {
        izena: "sudadera2",
        prezioa: 25,
        img: "img/Produktuak/sudadera2.png",
        kategoria: "sudaderak"
    },

    {
        izena: "sudadera3",
        prezioa: 25,
        img: "img/Produktuak/sudadera3.png",
        kategoria: "sudaderak"
    },

    {
        izena: "sudadera4",
        prezioa: 25,
        img: "img/Produktuak/sudadera4.png",
        kategoria: "sudaderak"
    },

    {
        izena: "sudadera5",
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


