import java.util.*;

public class InventoryApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ProductDAO dao = new ProductDAO();

        while (true) {
            System.out.println("\n=== Inventory Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;
            int ch;
            try { ch = Integer.parseInt(input); } catch (Exception e) { System.out.println("Enter a number."); continue; }

            if (ch == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Description: ");
                String desc = sc.nextLine();
                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                System.out.print("Price: ");
                double price = Double.parseDouble(sc.nextLine());

                Product p = new Product(name, desc, qty, price);
                dao.addProduct(p);
                System.out.println("Added: " + p);
            }

            else if (ch == 2) {
                List<Product> all = dao.getAll();
                if (all.isEmpty()) System.out.println("(no products)");
                else all.forEach(System.out::println);
            }

            else if (ch == 3) {
                System.out.print("Enter product id to update: ");
                int id = Integer.parseInt(sc.nextLine());
                Product p = dao.findById(id);
                if (p == null) {
                    System.out.println("No product with id " + id);
                    continue;
                }
                System.out.println("Current: " + p);

                System.out.print("New name (leave blank to keep): ");
                String name = sc.nextLine();
                if (!name.isBlank()) p.name = name;

                System.out.print("New description (leave blank to keep): ");
                String desc = sc.nextLine();
                if (!desc.isBlank()) p.description = desc;

                System.out.print("New quantity (leave blank to keep): ");
                String qline = sc.nextLine();
                if (!qline.isBlank()) p.quantity = Integer.parseInt(qline);

                System.out.print("New price (leave blank to keep): ");
                String pline = sc.nextLine();
                if (!pline.isBlank()) p.price = Double.parseDouble(pline);

                boolean ok = dao.updateProduct(p);
                System.out.println(ok ? "Updated successfully." : "Update failed.");
            }

            else if (ch == 4) {
                System.out.print("Enter product id to delete: ");
                int id = Integer.parseInt(sc.nextLine());
                System.out.print("Are you sure? (y/n): ");
                String confirm = sc.nextLine().trim().toLowerCase();
                if (!confirm.equals("y")) { System.out.println("Delete cancelled."); continue; }
                boolean ok = dao.deleteById(id);
                System.out.println(ok ? "Deleted." : "No product deleted (id not found).");
            }

            else if (ch == 0) {
                System.out.println("Bye!");
                break;
            }

            else {
                System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}
