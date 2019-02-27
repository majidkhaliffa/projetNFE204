# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://vagrantcloud.com/search.
 boxes = {
	    'BOX00' => { 'name' => 'meddev-TEST-1', 'box' => 'hashicorp/precise64', 'ip' => '192.168.0.70'},
		'BOX01' => { 'name' => 'meddev-TEST-2', 'box' => 'hashicorp/precise64', 'ip' => '192.168.0.71'},
		'BOX02' => { 'name' => 'meddev-TEST-3', 'box' => 'hashicorp/precise64', 'ip' => '192.168.0.72'},
	}
  
  # Disable automatic box update checking. If you disable this, then
  # boxes will only be checked for updates when the user runs
  # `vagrant box outdated`. This is not recommended.
  # config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # NOTE: This will enable public access to the opened port
   #config.vm.network "forwarded_port", guest: 80, host: 8080

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine and only allow access
  # via 127.0.0.1 to disable public access
   #config.vm.network "forwarded_port", guest: 80, host: 8080, host_ip: "127.0.0.1"

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  boxes.each do |num, data|
	  config.vm.define data['name']  do |rhel|
		  rhel.vm.box = data['box']
		  rhel.vm.hostname = data['name'] + ".vagrant"
          rhel.vm.network "private_network", ip: data['ip']
		  rhel.vm.network "public_network" , ip: data['ip']
  

  # Create a public network, which generally matched to bridged network.
  # Bridged networks make the machine appear as another physical device on
  # your network.
  

   
   #config.vm.provision "shell", path: "./scripts/add_new_disk.sh"
   #config.vm.provision "shell", path: "./scripts/bootstrap.sh"
  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"

  # Provider-specific configuration so you can fine-tune various
  # backing providers for Vagrant. These expose provider-specific options.
  # Example for VirtualBox:
  #
   
  rhel.vm.provider "virtualbox" do |vb|
  #   # Display the VirtualBox GUI when booting the machine
    # vb.gui = true
  #
  #   # Customize the amount of memory on the VM:
    # vb.memory = "1024"
	
	file_to_disk = File.realpath( "." ).to_s + "/disk_bis_" + num + ".vdi"

    if ARGV[0] == "up" && ! File.exist?(file_to_disk) 
       puts "Creating 5GB disk #{file_to_disk}."
       vb.customize [
            'createhd', 
            '--filename', file_to_disk, 
            '--format', 'VDI', 
            '--size', 5000 * 1024 # 5 GB
            ] 
       vb.customize [
            'storageattach', :id, 
            '--storagectl', 'SATA Controller', 
            '--port', 1, '--device', 0, 
            '--type', 'hdd', '--medium', 
            file_to_disk
            ]
			end
	    vb.customize [ "modifyvm", :id, "--memory", "2048" ]
		vb.customize [ "modifyvm", :id, "--cpus", "1" ]
		vb.customize [ "modifyvm", :id, "--usb", "off" ]
   end
   
  #
  # View the documentation for the provider you are using for more
  # information on available options.

  # Enable provisioning with a shell script. Additional provisioners such as
  # Puppet, Chef, Ansible, Salt, and Docker are also available. Please see the
  # documentation for more information about their specific syntax and use.
   config.vm.provision "shell", path: "./scripts/add_new_disk.sh"
   config.vm.provision "shell", path: "./scripts/bootstrap.sh"
end
	end

end