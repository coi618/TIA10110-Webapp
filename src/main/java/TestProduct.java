import org.hibernate.Session;
import org.hibernate.Transaction;

import com.product.model.Product;
import myutil.MyUtil;


public class TestProduct {

	public static void main(String[] args) {

		Session s1 = MyUtil.getSessionFactory().getCurrentSession();
		Transaction tx1 = null;

		// --- ShortCut functions ---------------
		// Read (get())
		try {
			tx1 = s1.beginTransaction();
			// begin TX
			
			
			Product p = s1.get(Product.class, 13000001);
			System.out.println(p);
			
			// End of tx
			tx1.commit();
		} catch (Exception e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} finally {
			MyUtil.shutdownSessionFactory();
		}

		// Read (load())

		// Add (save())

		// Update (update?)

		// Delete(delete()?)


		// --- HQL (CRUD) -----------------------

		// --- SQLQuery | NativeQuery (> Hibernate 5.2) ---

		// --- Criteria -------------------------

	}

}
