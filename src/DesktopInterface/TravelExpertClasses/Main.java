package DesktopInterface.TravelExpertClasses;

public class Main {

    public static void main(String[] args){
        for(PackageProductSupplier ps : PackageProductSupplierDB.GetProSupp(2))
        {
            System.out.println(ps + "/n");
        }
//
//        Product test = new Product();
//        long millis=System.currentTimeMillis();
//        java.sql.Date date=new java.sql.Date(millis);

//        test.setProdName("hello");
//        ProductDB.AddProduct(test);
//
//        Package newPackage = new Package();
//        newPackage.setPkgName("update");
//        newPackage.setPkgStartDate(date);
//        newPackage.setPkgEndDate(date);
//        newPackage.setPkgDesc("update");
//        newPackage.setPkgBasePrice(2000);
//        newPackage.setPkgAgencyCommission(2000);
//
//        Package oldPackage = PackageDB.GetPackages().get(9);
//
//        test.setPkgName("update");
//
//        PackageDB.UpdatePackage(oldPackage, newPackage);
//
//        for(Package p : PackageDB.GetPackages()){
//            System.out.println(p + "/n");

//        for(Product p : ProductDB.GetProducts()){
//            System.out.println(p + "\n");
//        }

    }
}
