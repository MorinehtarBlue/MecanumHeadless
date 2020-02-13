/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  private static final Gyro rioGyro = new ADXRS450_Gyro();

  private static final int kFrontLeftChannel = 1;
  private static final int kRearLeftChannel = 2;
  private static final int kFrontRightChannel = 3;
  private static final int kRearRightChannel = 4;

  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;

  @Override
  public void robotInit() {
    WPI_TalonFX frontLeft = new WPI_TalonFX(kFrontLeftChannel);
    WPI_TalonFX rearLeft = new WPI_TalonFX(kRearLeftChannel);
    WPI_TalonFX frontRight = new WPI_TalonFX(kFrontRightChannel);
    WPI_TalonFX rearRight = new WPI_TalonFX(kRearRightChannel);
    rioGyro.reset();

    // Invert the left side motors.
    // You may need to change or remove this to match your robot.
    //frontLeft.setInverted(true);
    //rearLeft.setInverted(true);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    m_stick = new Joystick(kJoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
    //m_robotDrive.driveCartesian(m_stick.getX()*.5, -m_stick.getY()*.5,m_stick.getZ()*.5, 0.0);

    double stickAngle = m_stick.getDirectionDegrees();
    double stickPower = m_stick.getMagnitude();
    double gyroAngle = rioGyro.getAngle() % 360;
    double driveAngle = stickAngle - gyroAngle;
    SmartDashboard.putNumber("aGyro", gyroAngle);
    SmartDashboard.putNumber("aStick:", stickAngle);
    SmartDashboard.putNumber("aDrive: ", driveAngle);
    m_robotDrive.drivePolar(Math.pow(stickPower, 3)*.5, driveAngle, Math.pow(m_stick.getZ(),3) * .35);
  }
}
