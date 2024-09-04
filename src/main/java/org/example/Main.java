package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Crear y persistir Domicilio
            Domicilio dom = new Domicilio("Calle Mitre", 2024);
            em.persist(dom);

            // Crear y persistir Cliente
            Cliente cliente = new Cliente("Ramiro", "Martinez", 44111111);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);
            em.persist(cliente);

            // Crear y persistir Categorias
            Categoria perecederos = new Categoria("Perecederos");
            Categoria lacteos = new Categoria("Lacteos");
            Categoria limpieza = new Categoria("Limpieza");
            em.persist(perecederos);
            em.persist(lacteos);
            em.persist(limpieza);

            // Crear y persistir Articulos
            Articulo art1 = new Articulo(200, "Yogurt Frutilla", 20);
            Articulo art2 = new Articulo(300, "Detergente", 80);
            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            perecederos.getArticulos().add(art1);
            lacteos.getArticulos().add(art1);
            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);
            em.persist(art1);
            em.persist(art2);

            // Crear y persistir Factura
            Factura factura1 = new Factura("10/08/2024", 12, 120);
            factura1.setCliente(cliente);
            em.persist(factura1);

            // Crear y persistir DetalleFactura
            DetalleFactura det1 = new DetalleFactura();
            det1.setArticulo(art1);
            det1.setCantidad(20);
            det1.setSubtotal(40);
            em.persist(det1);

            DetalleFactura det2 = new DetalleFactura();
            det1.setArticulo(art2);
            det1.setCantidad(1);
            det1.setSubtotal(30);
            em.persist(det1);


            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            // Cerrar el EntityManager y el EntityManagerFactory
            em.close();
            emf.close();
        }
    }
}
