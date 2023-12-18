package com.hexaware.dao;

import java.sql.*;

import java.util.ArrayList;

import com.hexaware.entity.*;
import com.hexaware.util.DBConnUtil;
import com.hexaware.exception.*;

public class CourierServiceDb {
	
	static Connection connection;
	
	public CourierServiceDb() {
		CourierServiceDb.connection = DBConnUtil.getConnection();
	}
	
	public boolean insertUserDetails(User user) {
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into user (Name,Email,password,ContactNumber,Address) values (?,?,?,?,?)");
			
			prepareStatement.setString(1,user.getUserName() );
			prepareStatement.setString(2, user.getEmail());
			prepareStatement.setString(3, user.getPassword());
			prepareStatement.setString(4, user.getContactNumber());
			prepareStatement.setString(5, user.getAddress());
			prepareStatement.execute();
			
		} 
		catch(SQLIntegrityConstraintViolationException e) {
			flag = false;
			System.out.println("The Email already exists. Provide an alternative email.");
			
		}
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}
	
	public boolean insertEmployeeDetails(Employee employee) {
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into employee (Name,Email,contactNumber,role,salary) values (?,?,?,?,?)");
			
			prepareStatement.setString(1,employee.getEmployeeName());
			prepareStatement.setString(2, employee.getEmail());
			prepareStatement.setString(3, employee.getContactNumber());
			prepareStatement.setString(4, employee.getRole());
			prepareStatement.setDouble(5, employee.getSalary());
			prepareStatement.execute();
			
		} 
		catch(SQLIntegrityConstraintViolationException e) {
			flag = false;
			System.out.println("The Email already exists. Provide an alternative email.");
			
		}
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}
	
	public ArrayList<User> retrieveUserDetails() {
		ArrayList<User> userArr = new ArrayList<>();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from user");
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				User user = new User(resultSet.getLong("userId"),resultSet.getString("Name"),resultSet.getString("Email"),
						resultSet.getString("password"),resultSet.getString("contactNumber"),resultSet.getString("Address"));
				userArr.add(user);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return userArr;
	}
	
	public User validateUser(String username,String password) {

		User user = new User();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from user where Name = ? and password = ?");
			prepareStatement.setString(1,username );
			prepareStatement.setString(2, password);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next()) {
				user.setUserId(resultSet.getLong("userId"));
				user.setUserName(resultSet.getString("Name"));
				user.setEmail(resultSet.getString("Email"));
				user.setPassword(resultSet.getString("password"));
				user.setContactNumber(resultSet.getString("contactNumber"));
				user.setAddress(resultSet.getString("Address"));
			}
			else {
				System.out.println("The username or password is incorrect\n");
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
		
	}
	
	public Employee validateEmployee(String username,String email) {

		Employee employee = new Employee();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from employee where Name = ? and email = ?");
			prepareStatement.setString(1,username );
			prepareStatement.setString(2, email);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next()) {
				employee.setEmployeeId(resultSet.getLong("employeeId"));
				employee.setEmployeeName(resultSet.getString("Name"));
				employee.setEmail(resultSet.getString("email"));
				employee.setContactNumber(resultSet.getString("contactNumber"));
				employee.setRole(resultSet.getString("role"));
				employee.setSalary(resultSet.getDouble("salary"));
			}
			else {
				System.out.println("The username or email is incorrect\n");
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
		
	}
	
	public long insertCourierDetails(Courier courier) {
		long courierId = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into courier (senderName,senderAddress,receiverName,receiverAddress,weight,status,trackingNumber,deliveryDate,userId) values"
					+ "(?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1,courier.getSenderName());
			preparedStatement.setString(2, courier.getSenderAddress());
			preparedStatement.setString(3, courier.getReceiverName());
			preparedStatement.setString(4, courier.getReceiverAddress());
			preparedStatement.setDouble(5, courier.getWeight());
			preparedStatement.setString(6, courier.getStatus());
			preparedStatement.setString(7, courier.getTrackingNumber());
			preparedStatement.setString(8, courier.getDeliveryDate());
			preparedStatement.setLong(9, courier.getUser().getUserId());
			
			preparedStatement.executeUpdate();
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				courierId = generatedKeys.getInt(1);
			}
			
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
		return courierId;
	}
	
	public long insertPaymentDetails(Payment payment) {
		long paymentId = 0;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("insert into payment (courierId,locationId,amount,paymentDate) values"
					+ "(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setLong(1,payment.getCourier().getCourierId());
			preparedStatement.setLong(2, payment.getLocation().getLocationId());
			preparedStatement.setDouble(3, payment.getAmount());
			preparedStatement.setString(4, payment.getPaymentDate());
			
			preparedStatement.executeUpdate();
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()) {
				paymentId = generatedKeys.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return paymentId;
	}
	
	public boolean insertOrderDetails(Order order) {
		boolean flag = true;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into orders (senderId,orderDate,courierId,serviceId,employeeId,senderLocationId,receiverLocationId,paymentId) values"
					+ "(?,?,?,?,?,?,?,?)");
			
			preparedStatement.setLong(1,order.getSender().getUserId());
			preparedStatement.setString(2, order.getOrderDate());
			preparedStatement.setLong(3, order.getCourier().getCourierId());
			preparedStatement.setLong(4, order.getService().getCourierServiceId());
			preparedStatement.setLong(5, order.getEmployee().getEmployeeId());
			preparedStatement.setLong(6, order.getSenderLocation().getLocationId());
			preparedStatement.setLong(7, order.getReceiverLocation().getLocationId());
			preparedStatement.setLong(8, order.getPayment().getPaymentId());
			
			preparedStatement.execute();
			
		} 
		catch (SQLException e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}
	
	public boolean checkLocationAvailability(String address) {
		
		String city = address.split(",")[1];
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from location where locationName = ?");
			preparedStatement.setString(1, city);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Location getLocation(String address){
		
		String city = address.split(",")[1];
		Location location = new Location();
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("Select * from location where locationName = ?");
			preparedStatement.setString(1, city);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				location.setLocationId(resultSet.getLong("locationId"));
				location.setLocationName(city);
				location.setAddress(resultSet.getString("address"));
				location.setLatitude(resultSet.getDouble("latitude"));
				location.setLongitude(resultSet.getDouble("longitude"));
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return location;
	}
	
	public ArrayList<CourierService> getCourierServices() {
		ArrayList<CourierService> csArr = new ArrayList<>();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Select * from courierServices");
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				CourierService cs = new CourierService(resultSet.getLong("ServiceId"),resultSet.getString("serviceName"),resultSet.getDouble("cost"));
				csArr.add(cs);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return csArr;
	}
	
	public User getUser(long userId) {
		User user = new User();
		
		PreparedStatement prepareStatement;
		try {
			prepareStatement = connection.prepareStatement("Select * from user where userId = ?");
			prepareStatement.setLong(1, userId);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next()) {
				user = new User(resultSet.getLong("userId"),resultSet.getString("name"),resultSet.getString("email"),
						resultSet.getString("password"),resultSet.getString("contactNumber"),resultSet.getString("address"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}
	
	
	public ArrayList<ArrayList<String>> getOrders(User user) {
		
		ArrayList<ArrayList<String>> osArr = new ArrayList<>();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select orderId,c.senderName,c.receiverName,c.trackingNumber,c.status,c.deliveryDate,p.amount from orders as o inner join user as u on o.senderId = u.userId inner join courier as c on c.courierId = o.courierId inner join payment as p on p.paymentId = o.paymentId where o.senderId = ?");
			prepareStatement.setLong(1, user.getUserId());
			
			ResultSet resultSet = prepareStatement.executeQuery();

			while(resultSet.next()) {
				ArrayList<String> tempArr = new ArrayList<>();
				
				tempArr.add(String.valueOf(resultSet.getLong("orderId")));
				tempArr.add(resultSet.getString("senderName"));
				tempArr.add(resultSet.getString("receiverName"));
				tempArr.add(resultSet.getString("trackingNumber"));
				tempArr.add(resultSet.getString("status"));
				tempArr.add(resultSet.getString("deliveryDate"));
				tempArr.add(String.valueOf(resultSet.getDouble("amount")));
				
				osArr.add(tempArr);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return osArr;
	}
	
	public String getOrderStatus(String trackingNumber) {
		String status = "";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select status from courier where trackingNumber = ?");
			prepareStatement.setString(1, trackingNumber);
			
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				status = resultSet.getString("status");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public double getCourierWeight(String trackingNumber) {
		double weight = 0;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select weight from courier where trackingNumber = ?");
			prepareStatement.setString(1, trackingNumber);
			
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				weight = resultSet.getDouble("weight");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return weight;
	}
	
	public boolean cancelOrder(String trackingNumber) {
		PreparedStatement prepareStatement;
		boolean flag = true;
		try {
			if(getOrderStatus(trackingNumber).equals("")) {
				flag = false;
				throw new TrackingNumberNotFoundException();
			}
			prepareStatement = connection.prepareStatement("delete from courier where trackingNumber = ?");
			prepareStatement.setString(1, trackingNumber);
			
			prepareStatement.execute();
		} 
		catch (SQLException | TrackingNumberNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
public ArrayList<ArrayList<String>> getAssignedOrders(long employeeId) {
		
		ArrayList<ArrayList<String>> osArr = new ArrayList<>();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select * from orders where employeeId = ?");
			prepareStatement.setLong(1, employeeId);
			
			ResultSet resultSet = prepareStatement.executeQuery();

			while(resultSet.next()) {
				ArrayList<String> tempArr = new ArrayList<>();
				
				tempArr.add(String.valueOf(resultSet.getLong("orderId")));
				tempArr.add(resultSet.getString("senderName"));
				tempArr.add(resultSet.getString("receiverName"));
				tempArr.add(resultSet.getString("trackingNumber"));
				tempArr.add(resultSet.getString("status"));
				tempArr.add(resultSet.getString("deliveryDate"));
				tempArr.add(String.valueOf(resultSet.getDouble("amount")));
				
				osArr.add(tempArr);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return osArr;
	}
	
	public Employee getEmployee() {
		
		Employee employee = new Employee();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select e.employeeId,e.name,e.email,e.contactNumber,e.role,e.salary, count(courierId) as c from orders o right join employee e on o.employeeId = e.employeeId where e.role <> ? group by e.employeeId order by c limit 1");
			prepareStatement.setString(1, "Manager");
			
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next()) {
				employee.setEmployeeId(resultSet.getLong("employeeId"));
				employee.setEmployeeName(resultSet.getString("name"));
				employee.setContactNumber(resultSet.getString("contactNumber"));
				employee.setRole(resultSet.getString("role"));
				employee.setSalary(resultSet.getDouble("salary"));
			}
		
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		return employee;
	}
	
	
	public static void main(String[] args) {
		CourierServiceDb csd = new CourierServiceDb();
		//System.out.println(csd.checkLocationAvailability("south,sirkali,tamilnadu"));
		System.out.println(csd.getEmployee());
	}
}
